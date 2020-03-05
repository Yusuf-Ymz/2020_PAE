package be.ipl.pae.persistance.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * à tester!
 * @author brunoloverius
 *
 */

public class DalServiceImpl implements DalService {
	private Connection conn = null;

	/**
	 * Crée une connexion à la DB.
	 * @param url l'URL de la DB à laquelle il faut se connecte.
	 * @param usr le USR de la DB
	 * @param password le mot de passe de la db
	 */
	public DalServiceImpl(String url, String usr, String password) {

		try { 
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) { 
			System.out.println("Driver PostgreSQL manquant !"); 
			System.exit(1);
		}
		try {
			this.conn=DriverManager.getConnection(url,usr,password); 
		} catch (SQLException e) {
			System.out.println("Impossible de joindre le server !");
			e.printStackTrace();
			System.exit(1); 
		}
	}

	/**
	 * Crée et exécute un querry.
	 * @param statement Le querry à exécuter
	 * @param attributes un tableau d'attributs classés dans l'ordre d'apparition dans le querry, null si aucun attributs.
	 */
	private void createAndExecuteStatement(String statement, Object... attributes) {
		try {
			PreparedStatement stmt = conn.prepareStatement(statement);
			for (int i = 0; i < attributes.length; i++) {
				stmt.setObject(i+1, attributes[i]);
			}
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
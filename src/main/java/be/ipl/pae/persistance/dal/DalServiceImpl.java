package be.ipl.pae.persistance.dal;

import be.ipl.pae.main.InjectionService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * à tester!
 * 
 * @author brunoloverius
 *
 */

public class DalServiceImpl implements DalService {
  private Connection conn = null;

  /**
   * Crée une connexion à la DB.
   * 
   * @param url l'URL de la DB à laquelle il faut se connecte.
   * @param usr le USR de la DB
   * @param password le mot de passe de la db
   */
  public DalServiceImpl() {

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    try {
      this.conn = DriverManager.getConnection(InjectionService.getConfiguration("url"),
          InjectionService.getConfiguration("user"), InjectionService.getConfiguration("password"));
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Crée et exécute un querry.
   * 
   * @param statement Le querry à exécuter
   * @param attributes un tableau d'attributs classés dans l'ordre d'apparition dans le querry, null
   *        si aucun attributs.
   * @return
   */
  public PreparedStatement createStatement(String statement, Object... attributes) {

    try {
      PreparedStatement stmt = conn.prepareStatement(statement);
      for (int i = 0; i < attributes.length; i++) {
        stmt.setObject(i + 1, attributes[i]);
      }
      return stmt;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}

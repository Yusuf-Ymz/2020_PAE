package be.ipl.pae.persistance.dal;

import be.ipl.pae.main.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


class DalServiceImpl implements DalService {
  private Connection conn = null;

  /**
   * Crée une connexion à la DB.
   * 
   */
  public DalServiceImpl() {

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException exception) {
      System.out.println("Driver PostgreSQL manquant !");
      exception.printStackTrace();
      System.exit(1);
    }
    try {
      this.conn = DriverManager.getConnection(Config.getConfiguration("url"),
          Config.getConfiguration("user"), Config.getConfiguration("password"));
    } catch (SQLException exception) {
      System.out.println("Impossible de joindre le server !");
      exception.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Crée un PreparedStatement et le renvoie.
   * 
   * @param statement Le querry à exécuter
   * @param attributes un tableau d'attributs classés dans l'ordre d'apparition dans le querry, null
   *        si aucun attributs.
   * @return stmt : le PreparedStatement créé
   */
  public PreparedStatement createStatement(String statement, Object... attributes) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement(statement);
      for (int i = 0; i < attributes.length; i++) {
        stmt.setObject(i + 1, attributes[i]);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return stmt;
  }
}

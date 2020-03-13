package be.ipl.pae.persistance.dal;

import be.ipl.pae.annotation.FieldDB;
import be.ipl.pae.main.Config;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


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
  public PreparedStatement createStatement(String statement) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement(statement);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return stmt;
  }

  /**
   * Rempli les attributs par introspection de l'objet passé en paramètre à partir des données en DB
   * (le ResultSet)
   * 
   * @param obj : l'objet
   * @param rs : le ResultSet
   */
  public void fillObject(Object obj, ResultSet rs) {
    Class<?> className = obj.getClass();
    Field[] fields = className.getDeclaredFields();
    try {
      for (Field field : fields) {
        if (field.isAnnotationPresent(FieldDB.class)) {
          field.setAccessible(true);
          Object dbObject = rs.getObject(field.getAnnotation(FieldDB.class).value());
          if (field.getType().equals(LocalDate.class)) {
            dbObject = ((Date) dbObject).toLocalDate();
          }
          field.set(obj, dbObject);
        }
      }
    } catch (SQLException | IllegalArgumentException | IllegalAccessException exception) {
      exception.printStackTrace();
    }
  }
}

package be.ipl.pae.persistance.dal;

import be.ipl.pae.annotation.FieldDb;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.main.Config;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


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
      throw new FatalException();
    }
  }


  public PreparedStatement createStatement(String statement) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement(statement);
    } catch (SQLException exception) {
      throw new FatalException();
    }
    return stmt;
  }


  public void fillObject(Object obj, Map<String, Object> dbObjects) {
    Class<?> className = obj.getClass();
    Field[] fields = className.getDeclaredFields();
    try {
      for (Field field : fields) {
        if (field.isAnnotationPresent(FieldDb.class)) {
          field.setAccessible(true);
          Object dbObject = dbObjects.get(field.getAnnotation(FieldDb.class).value());
          if (dbObject != null) {
            if (field.getType().equals(LocalDate.class)) {
              dbObject = ((Date) dbObject).toLocalDate();
            }
            field.set(obj, dbObject);
          }
        }
      }
    } catch (IllegalArgumentException | IllegalAccessException exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public Map<String, Object> convertResulSetToMap(ResultSet rs) {
    Map<String, Object> dbObjects = new HashMap<String, Object>();
    try {
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      for (int i = 1; i <= columnCount; i++) {
        String columnName = rsmd.getColumnName(i);
        Object obj = rs.getObject(i);
        dbObjects.put(columnName, obj);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
    return dbObjects;

  }
}

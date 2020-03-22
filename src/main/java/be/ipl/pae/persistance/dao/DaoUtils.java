package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.FieldDb;
import be.ipl.pae.exception.FatalException;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public abstract class DaoUtils {
  /**
   * Rempli les attributs par introspection de l'objet passé en paramètre à partir des données en DB
   * (le ResultSet).
   * 
   * @param obj : l'objet
   * @param rs : le ResultSet
   */
  public void fillObject(Object obj, ResultSet rs) {
    Class<?> className = obj.getClass();
    Field[] fields = className.getDeclaredFields();
    try {
      for (Field field : fields) {
        if (field.isAnnotationPresent(FieldDb.class)) {
          field.setAccessible(true);
          Object dbObject = rs.getObject(field.getAnnotation(FieldDb.class).value());
          if (dbObject != null) {
            if (field.getType().equals(LocalDate.class)) {
              dbObject = ((Date) dbObject).toLocalDate();
            }
            field.set(obj, dbObject);
          }

        }
      }
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
      throw new FatalException();
    } catch (IllegalArgumentException | IllegalAccessException exception) {
      exception.printStackTrace();
    }
  }
}

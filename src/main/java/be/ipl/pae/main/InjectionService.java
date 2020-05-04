package be.ipl.pae.main;

import be.ipl.pae.annotation.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class InjectionService {


  private static Map<String, Object> dependencies = new HashMap<String, Object>();

  /**
   * Injecte les dépendances de l'objet passé en paramètre.
   * 
   * @param obj : l'objet dont il faut injecter les dépendances
   */
  public void injectDependencies(Object obj) {
    Class<?> classe = obj.getClass();
    Field[] fields = classe.getDeclaredFields();
    try {
      for (Field field : fields) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(Inject.class)) {
          Object fieldObjet = null;
          Class<?> classAInjecter = field.getType();
          String classAInjecterName = classAInjecter.getName();
          String implName = Config.getConfiguration(classAInjecterName);
          if (dependencies.containsKey(implName)) {
            fieldObjet = dependencies.get(implName);
          } else {
            Class<?> classImpl = Class.forName(implName);
            Constructor<?> constructor = classImpl.getDeclaredConstructor();
            constructor.setAccessible(true);
            fieldObjet = constructor.newInstance();
            injectDependencies(fieldObjet);
            dependencies.put(implName, fieldObjet);
          }
          field.set(obj, fieldObjet);
        }
      }
    } catch (SecurityException | IllegalAccessException | IllegalArgumentException
        | ClassNotFoundException | NoSuchMethodException | InstantiationException
        | InvocationTargetException exception) {
      exception.printStackTrace();
    }
  }

}

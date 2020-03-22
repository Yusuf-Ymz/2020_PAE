package be.ipl.pae.main;

import be.ipl.pae.annotation.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class InjectionService {


  public static Map<String, Object> dependencies = new HashMap<String, Object>();

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

          if (dependencies.containsKey(classAInjecterName)) {
            fieldObjet = dependencies.get(classAInjecterName);
            // System.out.println("contenu " + fieldObjet.getClass().getCanonicalName());
          } else {
            String implName = Config.getConfiguration(classAInjecterName);
            Class<?> classImpl = Class.forName(implName);
            Constructor<?> constructor = classImpl.getDeclaredConstructor();
            constructor.setAccessible(true);
            fieldObjet = constructor.newInstance();
            // System.out.println("pas contenu " + fieldObjet.getClass().getCanonicalName());
            injectDependencies(fieldObjet);
            dependencies.put(classAInjecterName, fieldObjet);
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

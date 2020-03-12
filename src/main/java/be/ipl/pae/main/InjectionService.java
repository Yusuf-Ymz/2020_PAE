package be.ipl.pae.main;

import be.ipl.pae.annotation.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class InjectionService {


  Map<String, Object> dependencies = new HashMap<String, Object>();

  /**
   * Crée par introspection l'implementation de l'interface passée en parametre et la renvoie.
   * 
   * @param classe : la class de l'interface dont on veut l'implementation
   * @param params : la liste des paramétres à fournir pour pouvoir instancier l'objet
   * @return l'objet créer
   */
  @SuppressWarnings("unchecked")
  public void injectDependencies(Object obj) {
    Class classe = obj.getClass();
    Field[] fields = classe.getDeclaredFields();
    try {
      for (Field field : fields) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(Inject.class)) {
          Object fieldObjet = null;
          Class<?> classAIjnjecter = field.getType();
          String classAIjnjecterName = classAIjnjecter.getName();
          if (dependencies.containsKey(classAIjnjecterName)) {
            fieldObjet = dependencies.get(obj.getClass().getName());
          } else {
            String implName = Config.getConfiguration(classAIjnjecter.getName());
            Class<?> classImpl = Class.forName(implName);
            Constructor<?> constructor = classImpl.getDeclaredConstructor();
            constructor.setAccessible(true);
            fieldObjet = constructor.newInstance();
            injectDependencies(fieldObjet);
            dependencies.put(classAIjnjecterName, fieldObjet);
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

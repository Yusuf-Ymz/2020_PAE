package be.ipl.pae.main;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InjectionService {

  private static Properties props = new Properties();
  private static Map<String, Object> dependencies = new HashMap<String, Object>();

  static {
    try {
      FileInputStream file = new FileInputStream("prod.properties");
      props.load(file);
      file.close();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * 
   * renvoie la valeur de la propiete passée en paramétre.
   * 
   * @param propriete le nom (la clé) de la propriete recherché
   * @return la valeur de la propriete.
   */
  public static String getConfiguration(String propriete) {
    if (dependencies.containsKey(propriete)) {
      return (String) dependencies.get(propriete);
    }
    String value = (String) props.get(propriete);
    dependencies.put(propriete, value);
    return value;
  }

  /**
   * 
   * Crée par introspection l'implementation de l'interface passée en parametre et la renvoie.
   * 
   * @param c la class de l'interface dont on veut l'implementation
   * @param params la liste des paramétres à fournir pour pouvoir instancier l'objet
   * @return l'objet créer
   */
  public static <T> T getDependency(Class<?> c, Object... params) {
    String implName = props.getProperty(c.getName());
    if (dependencies.containsKey(implName)) {
      return (T) dependencies.get(implName);
    }
    try {
      Class<?> implClass = Class.forName(implName);
      for (Constructor<?> constructeur : implClass.getDeclaredConstructors()) {
        Class<?>[] paramsConstruct = constructeur.getParameterTypes();
        if (params.length == paramsConstruct.length) {
          constructeur.setAccessible(true);
          Object obj = constructeur.newInstance(params);
          dependencies.put(implName, obj);
          return (T) obj;
        }
      }
      return null;
    } catch (Throwable exception) {
      exception.printStackTrace();
      throw new RuntimeException(exception);
    }
  }

}

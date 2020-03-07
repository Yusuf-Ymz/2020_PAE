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
    } catch (Throwable exception) {
      throw new RuntimeException(exception);
    }
  }


  /**
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
   * Crée par introspection l'implementation de l'interface passée en parametre et la renvoie.
   * 
   * @param classe la class de l'interface dont on veut l'implementation
   * @param params la liste des paramétres à fournir pour pouvoir instancier l'objet
   * @return l'objet créer
   */
  public static <T> T getDependency(Class<?> classe, Object... params) {
    String implName = props.getProperty(classe.getName());
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

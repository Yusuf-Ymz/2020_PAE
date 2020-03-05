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

  public static String getConfiguration(String propriete) {
    if (dependencies.containsKey(propriete)) {
      return (String) dependencies.get(propriete);
    }
    String value = (String) props.get(propriete);
    dependencies.put(propriete, value);
    return value;
  }

  public static <T> T getDependency(Class<?> c, Object... params) {
    String implName = props.getProperty(c.getName());
    if (dependencies.containsKey(implName)) {
      return (T) dependencies.get(implName);
    }
    try {
      Class<?> implClass = Class.forName(implName);
      for (Constructor<?> constructeur : implClass.getDeclaredConstructors()) {
        Class<?>[] paramsConstruct = constructeur.getParameterTypes();
        if (params.length != paramsConstruct.length) {
          continue;
        }
        constructeur.setAccessible(true);
        return (T) constructeur.newInstance(params);
      }
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return null;
  }

}

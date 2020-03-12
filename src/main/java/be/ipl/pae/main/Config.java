package be.ipl.pae.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
  private static Properties props = new Properties();
  private static Map<String, Object> dependencies = new HashMap<String, Object>();

  public static void load(String pathname) {
    FileInputStream file;
    try {
      file = new FileInputStream(pathname);
      props.load(file);
      file.close();
    } catch (IOException exception) {
      exception.printStackTrace();
      throw new RuntimeException();
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

  public static int getConfigurationToInt(String propriete) {

    return Integer.parseInt(getConfiguration(propriete));
  }
}

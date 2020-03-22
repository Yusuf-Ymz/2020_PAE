package be.ipl.pae.persistance.dal;

import java.sql.PreparedStatement;

public interface DalBackendServices {

  /**
   * Crée un PreparedStatement relatif à  la requéte SQL et le renvoie.
   * 
   * @param statement Le querry Ã  exécuter
   * @return le PreparedStatement crée
   */
  PreparedStatement createStatement(String query);


}

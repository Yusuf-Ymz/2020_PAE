package be.ipl.pae.persistance.dal;

import java.sql.PreparedStatement;

public interface DalBackendServices {

  /**
   * Créé un PreparedStatement relatif à  la requéte SQL et le renvoi.
   * 
   * @param query : Le query à exécuter
   * @return le PreparedStatement créé
   */
  PreparedStatement createStatement(String query);


}

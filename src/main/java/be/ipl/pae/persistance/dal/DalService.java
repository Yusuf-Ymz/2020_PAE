package be.ipl.pae.persistance.dal;

import java.sql.PreparedStatement;

public interface DalService {

  String GET_USER = "SELECT p.* FROM pae.utilisateurs p WHERE p.pseudo = ?";

  /**
   * Cr�e un PreparedStatement relatif � la requ�te SQL.
   * 
   * @param statement
   * @param attributes
   * @return un PreparedStatement
   */
  PreparedStatement createStatement(String statement, Object... attributes);
}

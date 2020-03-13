package be.ipl.pae.persistance.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DalService {


  /**
   * Crée un PreparedStatement relatif à la requête SQL.
   * 
   * @param statement : le statement
   * @return un PreparedStatement
   */
  PreparedStatement createStatement(String statement);

  public void fillObject(Object obj, ResultSet rs);
}

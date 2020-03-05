package be.ipl.pae.persistance.dal;

import java.sql.PreparedStatement;

public interface DalService {

  String GET_USER = "SELECT * FROM pae.utilisateurs p WHERE p.pseudo = ?";

  public PreparedStatement createStatement(String statement, Object... attributes);
}

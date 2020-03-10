package mock.dal;

import be.ipl.pae.persistance.dal.DalService;

import java.sql.PreparedStatement;

public class MockDalService implements DalService {

  @Override
  public PreparedStatement createStatement(String statement, Object... attributes) {
    return null;
  }

}

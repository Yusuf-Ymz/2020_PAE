package mock.dao;

import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.persistance.dao.UserDao;

public class MockUserDao implements UserDao {

  private DtoFactory dtoFactory;

  public MockUserDao(DtoFactory dtoFactory) {
    this.dtoFactory = dtoFactory;
  }

  @Override
  public UserDto obtenirUser(String pseudo) {
    UserDto user = dtoFactory.getUserDto();
    user.setPseudo("pseudo");
    user.setPassword("mdp");
    user.setConfirme(true);
    return user;
  }
}

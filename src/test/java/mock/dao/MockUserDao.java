package mock.dao;

import be.ipl.pae.bizz.bizz.DtoFactory;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.persistance.dao.UserDao;

public class MockUserDao implements UserDao {

  private DtoFactory dtoFactory;

  @Override
  public UserDto obtenirUser(UserDto usr) {
    UserDto user = dtoFactory.getUserDto();
    user.setPseudo("pseudo");
    user.setPassword("mdp");
    // user.setConfirme(true);
    return user;
  }

}

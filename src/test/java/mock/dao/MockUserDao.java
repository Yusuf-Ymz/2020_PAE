package mock.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.persistance.dao.UserDao;

import java.util.List;

public class MockUserDao implements UserDao {

  @Inject
  private DtoFactory dtoFactory;

  @Override
  public UserDto obtenirUser(String pseudo) {

    if ("pseudo".equals(pseudo)) {
      UserDto user = dtoFactory.getUserDto();
      user.setPseudo("pseudo");
      user.setPassword("$2a$10$t04039EnUaPoJn8EpR3ovOkQXj1wEZwfUBNMmvHTnhN4FxZUSR0.S");
      user.setConfirme(true);
      return user;
    }
    return null;
  }

  @Override
  public List<UserDto> obtenirListeUser() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<UserDto> obtenirListeUsersPreInscrit() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UserDto obtenirUserAvecId(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addConfirmUserWithId(int idConfirmed) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addConfirmWorkerWithId(int idConfirmed) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeConfirmWorkerWithId(int userId) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeConfirmUserWithId(int userId) {
    // TODO Auto-generated method stub

  }
}

package mock.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.persistance.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class MockUserDao implements UserDao {

  @Inject
  private DtoFactory dtoFactory;

  @Override
  public UserDto obtenirUser(String pseudo) {
    if ("pseudo".equals(pseudo)) {
      UserDto user = dtoFactory.getUserDto();
      user.setPseudo("pseudo");
      user.setPassword("azerty");
      user.setConfirme(true);
      return user;
    }
    return null;
  }

  @Override
  public List<UserDto> obtenirListeUser() {

    List<UserDto> listeUser = new ArrayList<UserDto>();

    for (int i = 0; i < 5; i++) {
      UserDto user = this.dtoFactory.getUserDto();
      user.setClientId(i);
      user.setConfirme(true);
      listeUser.add(user);
    }

    return listeUser;
  }

  @Override
  public List<UserDto> obtenirListeUsersPreInscrit() {

    List<UserDto> listeUser = new ArrayList<UserDto>();

    for (int i = 0; i < 5; i++) {
      UserDto user = this.dtoFactory.getUserDto();
      user.setConfirme(false);
      listeUser.add(user);

    }

    return listeUser;

  }

  @Override
  public UserDto obtenirUserAvecId(int id) {
    UserDto user = dtoFactory.getUserDto();

    if (id == 1) {
      user.setOuvrier(true);
    } else {
      user.setOuvrier(false);
    }

    return user;
  }

  @Override
  public UserDto addUtilisateurClient(int idUser, int idClient) {

    UserDto user = dtoFactory.getUserDto();
    user.setUserId(idUser);
    user.setConfirme(true);
    user.setClientId(idClient);
    return user;
  }

  @Override
  public UserDto addConfirmWorkerWithId(int idConfirmed) {

    UserDto user = dtoFactory.getUserDto();

    user.setUserId(idConfirmed);
    user.setConfirme(true);
    user.setOuvrier(true);
    return user;
  }

  @Override
  public void inscrireUser(UserDto user) {

    UserDto mockUser = dtoFactory.getUserDto();
    mockUser = user;

  }

  @Override
  public boolean pseudoExiste(String pseudo) {
    if (pseudo.equals(" ")) {
      return false;
    }

    return true;
  }

  @Override
  public boolean emailExiste(String email) {
    if (email.equals(" ")) {
      return false;
    }

    return true;
  }

}

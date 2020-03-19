package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.persistance.dao.UserDao;

import java.util.List;

class UserUccImpl implements UserUcc {

  @Inject
  private UserDao userDao;

  public UserUccImpl() {

  }

  @Override
  public UserDto seConnecter(String pseudo, String password) {
    UserDto newUserDto = userDao.obtenirUser(pseudo);
    UserBiz userBiz = (UserBiz) newUserDto;

    if (userBiz == null || !userBiz.checkValidePassword(password) || !userBiz.isConfirme()) {
      return null;
    }
    return newUserDto;
  }


  @Override
  public List<UserDto> listerUsers(int userId) {
    UserDto userConnecte = obtenirUser(userId);
    if (userConnecte.isOuvrier()) {
      return userDao.obtenirListeUser();
    }
    /*
     * } else { json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
     * resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); }
     */
    return null;
  }

  @Override
  public List<UserDto> listerUsersPreinscrit(int userId) {
    UserDto userConnecte = obtenirUser(userId);
    if (userConnecte.isOuvrier()) {
      return userDao.obtenirListeUsersPreInscrit();
    }
    /*
     * } else { json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
     * resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); }
     */
    return null;
  }

  @Override
  public UserDto obtenirUser(int id) {
    return this.userDao.obtenirUserAvecId(id);
  }

  @Override
  public UserDto confirmUser(int userId, int idConfirmed) {
    UserDto userConnecte = obtenirUser(userId);
    if (userConnecte.isOuvrier()) {
      UserDto userConfirm = obtenirUser(idConfirmed);
      if (!userConfirm.isConfirme()) {
        userDao.addConfirmUserWithId(idConfirmed);
        return userConfirm;
      }
    }
    return null;
  }

  @Override
  public UserDto confirmWorker(int userId, int idConfirmed) {
    UserDto userConnecte = obtenirUser(userId);
    if (userConnecte.isOuvrier()) {
      UserDto userConfirm = obtenirUser(idConfirmed);
      if (!userConfirm.isConfirme()) {
        userDao.addConfirmWorkerWithId(idConfirmed);
        return userConfirm;
      }
    }
    return null;
  }

  @Override
  public void initilisation() {
    UserDto alain = userDao.obtenirUser("alain");
    UserDto hary = userDao.obtenirUser("hary");
    if (alain.isOuvrier()) {
      userDao.removeConfirmWorkerWithId(alain.getUserId());
    }
    if (hary.isOuvrier()) {
      userDao.removeConfirmWorkerWithId(hary.getUserId());
    }
    if (alain.isConfirme()) {
      System.out.println("coucou");
      userDao.removeConfirmUserWithId(alain.getUserId());
    }
    if (hary.isConfirme()) {
      userDao.removeConfirmUserWithId(hary.getUserId());
    }

  }

}

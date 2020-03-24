package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.UserDao;

import java.util.List;

class UserUccImpl implements UserUcc {

  @Inject
  private UserDao userDao;
  @Inject
  private DalServices dal;

  public UserUccImpl() {

  }

  @Override
  public UserDto seConnecter(String pseudo, String password) {
    try {
      dal.startTransaction();
      UserDto newUserDto = userDao.obtenirUser(pseudo);
      UserBiz userBiz = (UserBiz) newUserDto;
      if (userBiz == null || !userBiz.checkValidePassword(password) || !userBiz.isConfirme()
          || !userBiz.isOuvrier()) {
        throw new BizException("");
      }
      return newUserDto;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }


  }

  public void inscrire(UserDto user) {
    try {
      dal.startTransaction();

      if (userDao.pseudoExiste(user.getPseudo())) {
        throw new BizException("Pseudo déjà existant");
      }

      if (userDao.emailExiste(user.getEmail())) {
        throw new BizException("Email déjà existant");
      }

      userDao.inscrireUser(user);
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }


  @Override
  public List<UserDto> listerUsers(int userId) {
    try {
      dal.startTransaction();
      UserDto userConnecte = this.userDao.obtenirUserAvecId(userId);
      if (!userConnecte.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }
      return userDao.obtenirListeUser();
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }


  @Override
  public List<UserDto> listerUsersPreinscrit(int userId) {
    try {
      dal.startTransaction();
      UserDto userConnecte = this.userDao.obtenirUserAvecId(userId);
      if (!userConnecte.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }
      return userDao.obtenirListeUsersPreInscrit();
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }

  @Override
  public UserDto confirmUser(int userId, int idConfirmed) {
    try {
      dal.startTransaction();
      UserDto userConnecte = this.userDao.obtenirUserAvecId(userId);
      if (userConnecte.isOuvrier()) {
        UserDto userConfirm = this.userDao.obtenirUserAvecId(idConfirmed);
        if (!userConfirm.isConfirme()) {
          userDao.addConfirmUserWithId(idConfirmed);
          return userConfirm;
        }
      }
      throw new BizException(""); // TODO mettre un message correspondant
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }

  @Override
  public UserDto confirmWorker(int userId, int idConfirmed) {
    try {
      dal.startTransaction();
      UserDto userConnecte = this.userDao.obtenirUserAvecId(userId);
      if (userConnecte.isOuvrier()) {
        UserDto userConfirm = this.userDao.obtenirUserAvecId(idConfirmed);
        if (!userConfirm.isConfirme()) {
          userDao.addConfirmWorkerWithId(idConfirmed);
          return userConfirm;
        }
      }
      throw new BizException("");// TODO mettre un message correspondant
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }


  // à quoi ça sert ??????????????????????????????????????????????????????????
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

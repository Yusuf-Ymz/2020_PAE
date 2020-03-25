package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.ClientDao;
import be.ipl.pae.persistance.dao.UserDao;

import java.util.List;

class UserUccImpl implements UserUcc {

  @Inject
  private UserDao userDao;

  @Inject
  private ClientDao clientDao;

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
  public UserDto confirmUser(int ouvrierId, int idUser, int idClient) {
    try {
      dal.startTransaction();
      UserDto ouvrier = this.userDao.obtenirUserAvecId(ouvrierId);
      if (ouvrier.isOuvrier()) {
        UserDto utilisateur = this.userDao.obtenirUserAvecId(idUser);
        UserDto client = this.clientDao.rechercherClientAvecId(idClient);

        if (utilisateur != null && !utilisateur.isConfirme() && client != null
            && utilisateur.getClientId() == 0) {
          userDao.addUtilisateurClient(idUser, idClient);
          return utilisateur;
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


}

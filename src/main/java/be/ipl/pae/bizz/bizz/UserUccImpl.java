package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
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

      if (userBiz == null || !userBiz.checkValidePassword(password)) {
        throw new BizException("Données invalides");
      }


      if (!userBiz.isConfirme() && !userBiz.isOuvrier()) {
        throw new BizException("Vous n'êtes pas confirmé");
      }

      dal.commitTransaction();
      return newUserDto;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
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

      dal.commitTransaction();
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }


  @Override
  public List<UserDto> listerUsers() {
    try {

      dal.startTransaction();

      List<UserDto> users = userDao.obtenirListeUser();

      dal.commitTransaction();
      return users;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }


  @Override
  public List<UserDto> listerUsersPreinscrit() {

    try {
      dal.startTransaction();

      List<UserDto> users = userDao.obtenirListeUsersPreInscrit();

      dal.commitTransaction();
      return users;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public UserDto confirmUser(int idUser, int idClient) {
    try {
      dal.startTransaction();

      UserDto utilisateur = this.userDao.obtenirUserAvecId(idUser);
      ClientDto client = this.clientDao.rechercherClientAvecId(idClient);

      if (utilisateur != null && !utilisateur.isConfirme() && client != null
          && utilisateur.getClientId() == 0) {
        utilisateur = userDao.addUtilisateurClient(idUser, idClient);
        dal.commitTransaction();
        return utilisateur;
      }

      throw new BizException();
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public UserDto confirmWorker(int idConfirmed) {
    try {

      dal.startTransaction();

      UserDto userConfirm = this.userDao.obtenirUserAvecId(idConfirmed);

      if (userConfirm != null && !userConfirm.isConfirme()) {
        userConfirm = userDao.addConfirmWorkerWithId(idConfirmed);
        dal.commitTransaction();
        return userConfirm;
      }

      throw new BizException("utilisateur n'existe pas ou n'est pas confirmé");
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public UserDto obtenirUtilisateur(int userId) {
    try {
      dal.startTransaction();

      UserDto user = this.userDao.obtenirUserAvecId(userId);

      if (user == null) {
        throw new BizException("utilisateur introuvable");
      }

      dal.commitTransaction();
      return user;

    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }


}

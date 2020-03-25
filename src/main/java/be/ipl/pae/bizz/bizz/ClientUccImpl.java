package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.ClientDao;
import be.ipl.pae.persistance.dao.UserDao;

import java.util.List;

class ClientUccImpl implements ClientUcc {

  @Inject
  private UserDao userDao;
  @Inject
  private ClientDao clientDao;
  @Inject
  private DalServices dal;

  @Override
  public ClientDto insertClient(ClientDto client, int idOuvrier) {
    try {
      dal.startTransaction();
      UserDto user = userDao.obtenirUserAvecId(idOuvrier);

      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }

      return clientDao.insererClient(client);
    } catch (Exception exception) {
      exception.printStackTrace();
      dal.rollbackTransaction();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }

  @Override
  public List<ClientDto> listerClients(int idClient) {

    try {
      dal.startTransaction();
      UserDto user = userDao.obtenirUserAvecId(idClient);
      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }
      return this.clientDao.listerClients();
    } catch (Exception exception) {
      exception.printStackTrace();
      dal.rollbackTransaction();
      throw exception;
    } finally {
      dal.commitTransaction();
    }


  }

  @Override
  public List<ClientDto> listerClients(int idOuvrier, String nom, String prenom, String ville,
      String cp) {
    try {
      dal.startTransaction();
      UserDto user = userDao.obtenirUserAvecId(idOuvrier);
      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }
      return clientDao.rechercherClients(ville, cp, nom, prenom);
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }

  @Override
  public List<String> listerNomsClients(int idOuvrier, String nom) {
    try {
      dal.startTransaction();
      UserDto user = userDao.obtenirUserAvecId(idOuvrier);
      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }
      List<String> nomsClients = clientDao.rechercherNoms(nom);
      return nomsClients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }

  @Override
  public List<String> listerVilles(int idOuvrier, String ville) {
    try {
      dal.startTransaction();
      UserDto user = userDao.obtenirUserAvecId(idOuvrier);
      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }
      List<String> villes = clientDao.rechercherVilles(ville);
      return villes;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }

  @Override
  public List<String> listerCp(int idOuvrier, String cp) {
    try {
      dal.startTransaction();
      UserDto user = userDao.obtenirUserAvecId(idOuvrier);
      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }
      List<String> cpx = clientDao.rechercheCodePostaux(cp);
      return cpx;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }

  @Override
  public List<String> listerPrenomsClients(int idOuvrier, String prenom) {
    try {
      dal.startTransaction();
      UserDto user = userDao.obtenirUserAvecId(idOuvrier);
      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }
      List<String> prenomsClients = clientDao.rechercherPrenoms(prenom);
      return prenomsClients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }

  @Override
  public List<ClientDto> listerClientsPasUtilisateur(int idOuvrier) {
    try {
      dal.startTransaction();
      UserDto user = userDao.obtenirUserAvecId(idOuvrier);
      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }
      return clientDao.rechercherClientsPasUtilisateur();
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }



}

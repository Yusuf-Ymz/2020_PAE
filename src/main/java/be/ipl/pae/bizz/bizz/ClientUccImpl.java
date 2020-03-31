package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
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
  public ClientDto insertClient(ClientDto client) {
    try {
      dal.startTransaction();


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
  public List<ClientDto> listerClients() {

    try {
      dal.startTransaction();

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
  public List<ClientDto> listerClientsAvecCriteres(String nom, String prenom, String ville,
      String cp) {
    try {
      dal.startTransaction();

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
  public List<String> listerNomsClients(String nom) {
    try {
      dal.startTransaction();

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
  public List<String> listerVilles(String ville) {
    try {
      dal.startTransaction();

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
  public List<String> listerCp(String cp) {
    try {
      dal.startTransaction();

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
  public List<String> listerPrenomsClients(String prenom) {
    try {
      dal.startTransaction();

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
  public List<ClientDto> listerClientsPasUtilisateur() {
    try {
      dal.startTransaction();

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

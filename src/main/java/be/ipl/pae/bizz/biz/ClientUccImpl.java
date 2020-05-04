package be.ipl.pae.bizz.biz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.ClientDao;

import java.util.List;

class ClientUccImpl implements ClientUcc {


  @Inject
  private ClientDao clientDao;
  @Inject
  private DalServices dal;

  @Override
  public ClientDto insertClient(ClientDto client) {
    try {
      dal.startTransaction();
      ClientDto clientBis = clientDao.insererClient(client);
      dal.commitTransaction();
      return clientBis;
    } catch (Exception exception) {
      exception.printStackTrace();
      dal.rollbackTransaction();
      throw exception;
    }
  }

  @Override
  public List<ClientDto> listerClients() {

    try {
      dal.startTransaction();
      List<ClientDto> clients = this.clientDao.listerClients();
      dal.commitTransaction();
      return clients;
    } catch (Exception exception) {
      exception.printStackTrace();
      dal.rollbackTransaction();
      throw exception;
    }
  }

  @Override
  public List<ClientDto> listerClientsAvecCriteres(String nom, String prenom, String ville,
      String cp) {
    try {
      dal.startTransaction();
      List<ClientDto> clients = clientDao.rechercherClients(ville, cp, nom, prenom);
      dal.commitTransaction();
      return clients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerNomsClients(String nom) {
    try {
      dal.startTransaction();

      List<String> nomsClients = clientDao.rechercherNoms(nom);
      dal.commitTransaction();
      return nomsClients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerVilles(String ville) {
    try {
      dal.startTransaction();

      List<String> villes = clientDao.rechercherVilles(ville);
      dal.commitTransaction();
      return villes;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerCp(String cp) {
    try {
      dal.startTransaction();

      List<String> cpx = clientDao.rechercheCodePostaux(cp);
      dal.commitTransaction();
      return cpx;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerPrenomsClients(String prenom) {
    try {
      dal.startTransaction();

      List<String> prenomsClients = clientDao.rechercherPrenoms(prenom);
      dal.commitTransaction();
      return prenomsClients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<ClientDto> listerClientsPasUtilisateur() {
    try {
      dal.startTransaction();
      List<ClientDto> clients = clientDao.rechercherClientsPasUtilisateur();
      dal.commitTransaction();
      return clients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerNomsClientsNonLie(String nom) {
    try {
      dal.startTransaction();

      List<String> nomsClients = clientDao.rechercherNomsClientNonLie(nom);
      dal.commitTransaction();
      return nomsClients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerPrenomsClientsNonLie(String prenom) {
    try {
      dal.startTransaction();

      List<String> prenomsClients = clientDao.rechercherPrenomsClientNonLie(prenom);
      dal.commitTransaction();
      return prenomsClients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerVillesClientsNonLie(String ville) {

    try {
      dal.startTransaction();

      List<String> villes = clientDao.rechercherVillesClientNonLie(ville);
      dal.commitTransaction();
      return villes;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerCpClientsNonLie(String cp) {
    try {
      dal.startTransaction();

      List<String> cpx = clientDao.rechercheCodePostauxClientNonLie(cp);
      dal.commitTransaction();
      return cpx;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<ClientDto> listerClientsNonLieAvecCriteres(String nom, String prenom, String ville,
      String cp) {
    try {
      dal.startTransaction();
      List<ClientDto> clients = clientDao.rechercherClientsNonLie(ville, cp, nom, prenom);
      dal.commitTransaction();
      return clients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }
}

package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dao.ClientDao;
import be.ipl.pae.persistance.dao.UserDao;

import java.util.List;

class ClientUccImpl implements ClientUcc {

  @Inject
  private UserDao userDao;

  @Inject
  private ClientDao clientDao;

  @Override
  public ClientDto insertClient(ClientDto client, int idOuvrier) {
    UserDto user = userDao.obtenirUserAvecId(idOuvrier);
    if (!user.isOuvrier())
      throw new BizException("Vous n'avez pas les droits");
    return clientDao.insererClient(client);
  }

  @Override
  public List<ClientDto> listerClients(int idClient) {


    UserDto user = userDao.obtenirUserAvecId(idClient);


    if (!user.isOuvrier())
      return null;


    return this.clientDao.listerClients();

  }

  @Override
  public List<ClientDto> listerClients(int idOuvrier, String nom, String prenom, String ville,
      String cp) {
    UserDto user = userDao.obtenirUserAvecId(idOuvrier);
    if (!user.isOuvrier())
      throw new BizException("Vous n'avez pas les droits");
    return clientDao.rechercherClients(ville, cp, nom, prenom);
  }

  @Override
  public List<String> listerNomsClients(int idOuvrier, String nom) {
    UserDto user = userDao.obtenirUserAvecId(idOuvrier);
    if (!user.isOuvrier())
      throw new BizException("Vous n'avez pas les droits");
    List<String> nomsClients = clientDao.rechercherNoms(nom);
    return nomsClients;
  }

  @Override
  public List<String> listerVilles(int idOuvrier, String ville) {
    UserDto user = userDao.obtenirUserAvecId(idOuvrier);
    if (!user.isOuvrier())
      throw new BizException("Vous n'avez pas les droits");
    List<String> villes = clientDao.rechercherVilles(ville);
    return villes;
  }

  @Override
  public List<String> listerCp(int idOuvrier, String cp) {
    UserDto user = userDao.obtenirUserAvecId(idOuvrier);
    if (!user.isOuvrier())
      throw new BizException("Vous n'avez pas les droits");
    List<String> cpx = clientDao.rechercheCodePostaux(cp);
    return cpx;
  }

  @Override
  public List<String> listerPrenomsClients(int idOuvrier, String prenom) {
    UserDto user = userDao.obtenirUserAvecId(idOuvrier);
    if (!user.isOuvrier())
      throw new BizException("Vous n'avez pas les droits");
    List<String> prenomsClients = clientDao.rechercherPrenoms(prenom);
    return prenomsClients;
  }



}

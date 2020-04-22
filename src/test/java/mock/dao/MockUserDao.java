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
    UserDto user = dtoFactory.getUserDto();
    if ("pseudo".equals(pseudo)) {
      user.setPseudo("pseudo");
      user.setPassword("azerty");
      user.setConfirme(true);
      return user;
    } else if ("nonConfirme".equals(pseudo)) {
      user.setPseudo("nonConfirme");
      user.setPassword("azerty");
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
      user.setConfirme(true);
    } else if (id == 3) {
      user.setConfirme(true);
      return user;
    } else if (id == 10) {
      return null;
    }
    return user;
  }

  @Override
  public UserDto addUtilisateurClient(int idUser, int idClient) {

    UserDto user = dtoFactory.getUserDto();
    user.setUserId(idUser);
    user.setConfirme(true);
    user.setClientId(idClient);

    System.out.println(user);
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


  }

  @Override
  public boolean pseudoExiste(String pseudo) {
    if (!pseudo.equals(" ")) {
      return false;
    }

    return true;
  }

  @Override
  public boolean emailExiste(String email) {
    if (!email.equals(" ")) {
      return false;
    }

    return true;
  }

  @Override
  public List<String> rechercherNomsUtilisateurs(String nom) {
    // TODO Auto-generated method stub
    List<String> noms = new ArrayList<String>();

    if (nom == "test") {
      noms.add("hello");
    }

    return noms;
  }

  @Override
  public List<String> rechercherPrenomsUtilisateurs(String prenom) {
    // TODO Auto-generated method stub
    List<String> prenoms = new ArrayList<String>();

    if (prenom == "test") {
      prenoms.add("hello");
    }

    return prenoms;
  }

  @Override
  public List<String> rechercherVillesUtilisateurs(String ville) {
    // TODO Auto-generated method stub
    List<String> villes = new ArrayList<String>();

    if (ville == "test") {
      villes.add("hello");
    }

    return villes;
  }

  @Override
  public List<UserDto> rechercherUtilisateurs(String nom, String prenom, String ville) {
    // TODO Auto-generated method stub
    List<UserDto> users = new ArrayList<UserDto>();

    if (nom == "yilmaz" && prenom == null && ville == null) {
      users.add(dtoFactory.getUserDto());
    }

    if (nom == "yaffa" && prenom == "elie" && ville == null) {
      users.add(dtoFactory.getUserDto());
    }

    if (nom == "okou" && prenom == "gnakouri" && ville != null) {
      users.add(dtoFactory.getUserDto());
    }


    return users;
  }

}

package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalBackendServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


class UserDaoImpl extends DaoUtils implements UserDao {
  @Inject
  private DalBackendServices dal;
  @Inject
  private DtoFactory fact;

  public UserDaoImpl() {

  }

  @Override
  public UserDto obtenirUser(String pseudo) {
    String query = "SELECT u.* FROM pae.utilisateurs u WHERE u.pseudo = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setString(1, pseudo);
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        return null;
      }
      UserDto user = fact.getUserDto();
      fillObject(user, rs);
      return user;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public List<UserDto> obtenirListeUser() {
    String query = "SELECT * FROM pae.utilisateurs ORDER BY nom";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      ResultSet rs = prepareStatement.executeQuery();

      List<UserDto> listeUsers = new ArrayList<UserDto>();
      while (rs.next()) {
        UserDto user = fact.getUserDto();
        fillObject(user, rs);
        listeUsers.add(user);
      }
      return listeUsers;

    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  @Override
  public List<UserDto> obtenirListeUsersPreInscrit() {
    String query = "SELECT * FROM pae.utilisateurs u WHERE u.confirme = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setBoolean(1, false);
      ResultSet rs = prepareStatement.executeQuery();

      List<UserDto> listeUsers = new ArrayList<UserDto>();
      while (rs.next()) {
        UserDto user = fact.getUserDto();
        fillObject(user, rs);
        listeUsers.add(user);
      }
      return listeUsers;

    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  @Override
  public UserDto obtenirUserAvecId(int id) {
    String query = "Select * FROM pae.utilisateurs WHERE utilisateur_id = ? ";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, id);
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        return null;
      }
      UserDto user = fact.getUserDto();
      fillObject(user, rs);
      return user;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }

  @Override
  public UserDto addConfirmWorkerWithId(int newOuvrierId) {
    String query =
        "UPDATE pae.utilisateurs SET confirme = true, ouvrier = true WHERE utilisateur_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, newOuvrierId);
      prepareStatement.execute();
      return this.obtenirUserAvecId(newOuvrierId);
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  public void inscrireUser(UserDto user) {
    String query = "INSERT INTO pae.utilisateurs VALUES(DEFAULT,false,false,?,?,?,null,?,?,?,?);";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {

      prepareStatement.setDate(1, java.sql.Date.valueOf(user.getDateInscription()));
      prepareStatement.setString(2, user.getPseudo());
      prepareStatement.setString(3, user.getPassword());
      prepareStatement.setString(4, user.getNom());
      prepareStatement.setString(5, user.getPrenom());
      prepareStatement.setString(6, user.getVille());
      prepareStatement.setString(7, user.getEmail());

      prepareStatement.execute();
      System.out.println("Ajout complet");
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  public boolean pseudoExiste(String pseudo) {

    String query = "SELECT * FROM pae.utilisateurs WHERE pseudo = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setString(1, pseudo);
      ResultSet rs = prepareStatement.executeQuery();

      if (rs.next()) {
        return true;
      }

      return false;
    } catch (SQLException exception) {
      throw new FatalException();
    }
  }

  public boolean emailExiste(String email) {
    String query = "SELECT * FROM pae.utilisateurs WHERE LOWER(email) = LOWER(?)";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setString(1, email);
      ResultSet rs = prepareStatement.executeQuery();

      if (rs.next()) {
        return true;
      }

      return false;
    } catch (SQLException exception) {
      throw new FatalException();
    }
  }

  @Override
  public UserDto addUtilisateurClient(int idUser, int idClient) {
    // ne fonctionne pas
    String query =
        "UPDATE pae.utilisateurs SET confirme = true, client_id = ? WHERE utilisateur_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, idClient);
      prepareStatement.setInt(2, idUser);
      prepareStatement.execute();

      return this.obtenirUserAvecId(idUser);
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public List<String> rechercherNomsUtilisateurs(String nom) {
    nom = nom.replace("%", "\\" + "%");
    nom += "%";
    String query =
        "SELECT DISTINCT u.nom FROM pae.utilisateurs u " + "WHERE LOWER(u.nom) LIKE LOWER(?)";

    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> noms = new ArrayList<String>();
    try {
      prepareStatement.setString(1, nom);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String nomDb = rs.getString(1);
        noms.add(nomDb);
      }
      return noms;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<String> rechercherPrenomsUtilisateurs(String prenom) {
    prenom = prenom.replace("%", "\\" + "%");
    prenom += "%";
    String query = "SELECT DISTINCT u.prenom FROM pae.utilisateurs u "
        + "WHERE LOWER(u.prenom) LIKE LOWER (?)";

    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> prenoms = new ArrayList<String>();
    try {
      prepareStatement.setString(1, prenom);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String prenomDb = rs.getString(1);
        prenoms.add(prenomDb);
      }
      return prenoms;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<String> rechercherVillesUtilisateurs(String ville) {
    ville = ville.replace("%", "\\" + "%");
    ville += "%";
    String query =
        "SELECT DISTINCT u.ville FROM pae.utilisateurs u " + "WHERE LOWER(u.ville) LIKE LOWER (?)";

    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> villes = new ArrayList<String>();
    try {
      prepareStatement.setString(1, ville);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String villesDb = rs.getString(1);
        villes.add(villesDb);
      }
      return villes;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<UserDto> rechercherUtilisateurs(String nom, String prenom, String ville) {
    ville = ville.replace("%", "\\" + "%");
    ville += "%";

    prenom = prenom.replace("%", "\\" + "%");
    prenom += "%";

    nom = nom.replace("%", "\\" + "%");
    nom += "%";

    String query = "SELECT * FROM pae.utilisateurs u " + "WHERE LOWER(u.nom) LIKE LOWER(?) AND "
        + "LOWER(u.prenom) LIKE LOWER(?) AND " + "LOWER(u.ville) LIKE LOWER(?) "
        + "ORDER BY u.nom ";
    return executeQueryRechercheUtilisateur(query, nom, prenom, ville);
  }

  @Override
  public List<String> rechercherNomsUtilisateursNonConfirme(String nom) {
    nom = nom.replace("%", "\\" + "%");
    nom += "%";
    String query = "SELECT DISTINCT u.nom FROM pae.utilisateurs u "
        + "WHERE LOWER(u.nom) LIKE LOWER (?) AND u.confirme ='false' ";

    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> noms = new ArrayList<String>();
    try {
      prepareStatement.setString(1, nom);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String nomDb = rs.getString(1);
        noms.add(nomDb);
      }
      return noms;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }

  }

  @Override
  public List<String> rechercherPrenomsUtilisateursNonConfirme(String prenom) {
    prenom = prenom.replace("%", "\\" + "%");
    prenom += "%";
    String query = "SELECT DISTINCT u.prenom FROM pae.utilisateurs u "
        + "WHERE LOWER(u.prenom) LIKE LOWER (?) AND u.confirme ='false' ";

    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> prenoms = new ArrayList<String>();
    try {
      prepareStatement.setString(1, prenom);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String prenomDb = rs.getString(1);
        prenoms.add(prenomDb);
      }
      return prenoms;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<String> rechercherVillesUtilisateursNonConfirme(String ville) {
    ville = ville.replace("%", "\\" + "%");
    ville += "%";
    String query = "SELECT DISTINCT u.ville FROM pae.utilisateurs u "
        + "WHERE LOWER(u.ville) LIKE LOWER (?) AND u.confirme ='false' ";

    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> villes = new ArrayList<String>();
    try {
      prepareStatement.setString(1, ville);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String villesDb = rs.getString(1);
        villes.add(villesDb);
      }
      return villes;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<UserDto> rechercherUtilisateurNonConfirme(String nom, String prenom, String ville) {
    ville = ville.replace("%", "\\" + "%");
    ville += "%";

    prenom = prenom.replace("%", "\\" + "%");
    prenom += "%";

    nom = nom.replace("%", "\\" + "%");
    nom += "%";

    String query = "SELECT * FROM pae.utilisateurs u " + "WHERE LOWER(u.nom) LIKE LOWER(?) AND "
        + "LOWER(u.prenom) LIKE LOWER(?) AND "
        + "LOWER(u.ville) LIKE LOWER(?) AND u.confirme ='false' " + "ORDER BY u.nom ";

    return executeQueryRechercheUtilisateur(query, nom, prenom, ville);
  }



  private List<UserDto> executeQueryRechercheUtilisateur(String query, String nom, String prenom,
      String ville) {
    PreparedStatement prepareStatement = dal.createStatement(query);
    List<UserDto> users = new ArrayList<UserDto>();
    try {
      prepareStatement.setString(1, nom);
      prepareStatement.setString(2, prenom);
      prepareStatement.setString(3, ville);

      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        UserDto user = fact.getUserDto();
        fillObject(user, rs);
        users.add(user);
      }

      return users;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

}



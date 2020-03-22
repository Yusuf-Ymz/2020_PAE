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
  public void addConfirmUserWithId(int idConfirmed) {
    String query = "UPDATE pae.utilisateurs SET confirme = true WHERE utilisateur_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, idConfirmed);
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        // exception
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public void addConfirmWorkerWithId(int idConfirmed) {
    String query =
        "UPDATE pae.utilisateurs SET confirme = true, ouvrier = true WHERE utilisateur_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, idConfirmed);
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        // exception
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public void removeConfirmWorkerWithId(int userId) {
    String query =
        "UPDATE pae.utilisateurs SET confirme = false, ouvrier = false WHERE utilisateur_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, userId);
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        // exception
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public void removeConfirmUserWithId(int userId) {
    String query = "UPDATE pae.utilisateurs SET confirme = false WHERE utilisateur_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, userId);
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        // exceptions
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  public void inscrireUser(UserDto user) {
    String query = "INSERT INTO pae.utilisateurs VALUES(DEFAULT,false,false,?,?,?,null,?,?,?,?);";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      // CHANGER LA DATE.TOSTRING!!!!!!!!!!!
      prepareStatement.setString(1, user.getDateInscription().toString());
      prepareStatement.setString(2, user.getPseudo());
      prepareStatement.setString(3, user.getPassword());
      prepareStatement.setString(4, user.getNom());
      prepareStatement.setString(5, user.getPrenom());
      prepareStatement.setString(6, user.getVille());
      prepareStatement.setString(7, user.getEmail());

      prepareStatement.executeQuery(query);
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

      if (rs.next())
        return true;

      return false;
    } catch (SQLException exception) {
      throw new FatalException();
    }
  }

  public boolean emailExiste(String email) {
    String query = "SELECT * FROM pae.utilisateurs WHERE email = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setString(1, email);
      ResultSet rs = prepareStatement.executeQuery();

      if (rs.next())
        return true;

      return false;
    } catch (SQLException exception) {
      throw new FatalException();
    }
  }
}



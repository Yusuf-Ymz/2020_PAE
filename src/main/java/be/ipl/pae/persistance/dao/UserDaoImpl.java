package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


class UserDaoImpl implements UserDao {
  @Inject
  private DalService dal;
  @Inject
  private DtoFactory fact;

  public UserDaoImpl() {

  }

  public UserDaoImpl(DalService dal, DtoFactory fact) {
    this.dal = dal;
    this.fact = fact;
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
      dal.fillObject(user, rs);
      return user;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }


}

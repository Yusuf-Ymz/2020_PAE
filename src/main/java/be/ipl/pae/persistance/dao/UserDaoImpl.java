package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.bizz.DtoFactory;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.persistance.dal.DalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoImpl implements UserDao {

  private DalService dal;
  private DtoFactory fact;


  public UserDaoImpl(DalService dal, DtoFactory fact) {
    this.dal = dal;
    this.fact = fact;
  }

  @Override
  public UserDto obtenirUser(UserDto usr) {
    PreparedStatement prepareStatement = dal.createStatement(DalService.GET_USER, usr.getPseudo());
    try {
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        return null;
      }
      UserDto user = fact.getUserDto();
      user.setPassword(rs.getString(6));
      user.setConfirme(rs.getBoolean(2));
      return user;
    } catch (SQLException exception) {

      exception.printStackTrace();
    }
    return null;
  }

}

package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
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
    PreparedStatement prepareStatement = dal.createStatement(DalService.GET_USER, pseudo);
    try {
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        return null;
      }
      UserDto user = fact.getUserDto();
      user.setUserId(rs.getInt(1));
      user.setConfirme(rs.getBoolean(2));
      user.setOuvrier(rs.getBoolean(3));
      user.setDateInscription(rs.getDate(4).toLocalDate());
      user.setPseudo(pseudo);
      user.setPassword(rs.getString(6));
      // user.setClientId(rs.getInt(7));
      user.setNom(rs.getString(8));
      user.setPrenom(rs.getString(9));
      user.setVille(rs.getString(10));
      user.setEmail(rs.getString(11));


      return user;
    } catch (SQLException exception) {

      exception.printStackTrace();
    }
    return null;
  }

}

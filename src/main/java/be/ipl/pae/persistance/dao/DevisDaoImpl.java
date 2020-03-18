package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevisDaoImpl implements DevisDao {
  @Inject
  private DalService dal;
  @Inject
  private DtoFactory fact;

  @Override
  public UserDto obtenirUserAvecId(int idUser) {
    String query = "Select * FROM pae.utilisateurs WHERE utilisateur_id = ? ";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, idUser);
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        return null;
      }
      UserDto user = fact.getUserDto();
      dal.fillObject(user, dal.convertResulSetToMap(rs));
      return user;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public List<DevisDto> obtenirTousLesDevis() {
    String query = "SELECT * FROM pae.devis ORDER BY date_debut";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      ResultSet rs = prepareStatement.executeQuery();

      List<DevisDto> listeDevis = new ArrayList<DevisDto>();
      while (rs.next()) {
        DevisDto devis = fact.getDevisDto();
        dal.fillObject(devis, dal.convertResulSetToMap(rs));
        listeDevis.add(devis);
      }
      return listeDevis;

    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }

}
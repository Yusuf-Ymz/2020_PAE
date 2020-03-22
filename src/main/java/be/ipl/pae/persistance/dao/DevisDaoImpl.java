package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalBackendServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevisDaoImpl extends DaoUtils implements DevisDao {
  @Inject
  private DalBackendServices dal;
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
      fillObject(user, rs);
      return user;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public List<DevisDto> obtenirTousLesDevis() {
    String query =
        "SELECT * FROM pae.devis d LEFT OUTER JOIN pae.photos p ON d.photo_preferee=p.photo_id ORDER BY date_debut";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      ResultSet rs = prepareStatement.executeQuery();

      List<DevisDto> listeDevis = new ArrayList<DevisDto>();
      while (rs.next()) {
        DevisDto devis = fact.getDevisDto();
        fillObject(devis, rs);
        listeDevis.add(devis);
      }
      return listeDevis;

    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  public List<DevisDto> obtenirSesDevis(int idClient) {
    String query =
        "SELECT * FROM pae.devis d LEFT OUTER JOIN pae.photos p ON d.photo_preferee=p.photo_id WHERE d.client = ? ORDER BY date_debut";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, idClient);
      ResultSet rs = prepareStatement.executeQuery();

      List<DevisDto> listeDevis = new ArrayList<DevisDto>();
      while (rs.next()) {
        DevisDto devis = fact.getDevisDto();
        fillObject(devis, rs);
        listeDevis.add(devis);
      }
      return listeDevis;

    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  @Override
  public DevisDto insererDevis(int idOuvrier, DevisDto devis) {
    String query = "";
    PreparedStatement prepareStatement = dal.createStatement(query);

    return null;
  }

}

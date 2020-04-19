package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalBackendServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class AmenagementDaoImpl extends DaoUtils implements AmenagementDao {
  @Inject
  private DalBackendServices dal;
  @Inject
  private DtoFactory fact;

  @Override
  public List<AmenagementDto> listerLesAmenagements() {
    List<AmenagementDto> amenagements = new ArrayList<AmenagementDto>();

    String query = "select * from pae.types_amenagements";
    PreparedStatement pstatement = dal.createStatement(query);
    try {
      ResultSet rs = pstatement.executeQuery();
      while (rs.next()) {
        AmenagementDto amenagement = fact.getAmenagementDto();
        fillObject(amenagement, rs);
        amenagements.add(amenagement);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

    return amenagements;
  }

  @Override
  public AmenagementDto getAmenagementById(int id) {
    String query = "SELECT * FROM pae.types_amenagements WHERE type_amenagement = ?";
    PreparedStatement ps = dal.createStatement(query);
    try {
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        AmenagementDto amenagement = fact.getAmenagementDto();
        fillObject(amenagement, rs);
        return amenagement;
      }
      return null;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }

  @Override
  public AmenagementDto ajouterAmenagment(AmenagementDto amenagement) {
    String query =
        "INSERT INTO pae.types_amenagements VALUES (DEFAULT,?) RETURNING type_amenagement";
    PreparedStatement ps = dal.createStatement(query);
    try {
      ps.setString(1, amenagement.getLibelle());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return getAmenagementById(rs.getInt(1));
      }
      return null;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

}

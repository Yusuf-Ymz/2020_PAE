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

}

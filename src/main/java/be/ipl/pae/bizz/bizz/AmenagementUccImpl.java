package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.ucc.AmenagementUcc;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.AmenagementDao;

import java.util.List;

class AmenagementUccImpl implements AmenagementUcc {

  @Inject
  private AmenagementDao amenagementDao;

  @Inject
  private DalServices dal;

  @Override
  public List<AmenagementDto> listerTousLesAmenagements() {
    try {
      dal.startTransaction();

      List<AmenagementDto> amenagements = amenagementDao.listerLesAmenagements();

      dal.commitTransaction();
      return amenagements;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public AmenagementDto ajouterAmenagement(AmenagementDto amenagement) {
    try {
      dal.startTransaction();

      AmenagementDto amenagementBis = amenagementDao.ajouterAmenagment(amenagement);

      dal.commitTransaction();
      return amenagementBis;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

}

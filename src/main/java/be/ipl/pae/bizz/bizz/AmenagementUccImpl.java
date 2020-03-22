package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.ucc.AmenagementUcc;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.AmenagementDao;

import java.util.List;

class AmenagementUccImpl implements AmenagementUcc {

  @Inject
  AmenagementDao amenagementDao;

  @Inject
  DalServices dal;

  @Override
  public List<AmenagementDto> listerTousLesAmenagements() {
    try {
      dal.startTransaction();
      return amenagementDao.listerLesAmenagements();
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }

}

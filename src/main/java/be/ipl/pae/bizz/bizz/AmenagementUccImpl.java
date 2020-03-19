package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.ucc.AmenagementUcc;
import be.ipl.pae.persistance.dao.AmenagementDao;

import java.util.List;

class AmenagementUccImpl implements AmenagementUcc {

  @Inject
  AmenagementDao amenagementDao;

  @Override
  public List<AmenagementDto> listerTousLesAmenagements() {
    return amenagementDao.listerLesAmenagements();
  }

}

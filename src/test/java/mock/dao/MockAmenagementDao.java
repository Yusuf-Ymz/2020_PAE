package mock.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.persistance.dao.AmenagementDao;

import java.util.List;

public class MockAmenagementDao implements AmenagementDao {

  @Inject
  private DtoFactory dtoFactory;

  @Override
  public List<AmenagementDto> listerLesAmenagements() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AmenagementDto getAmenagementById(int id) {
    // TODO Auto-generated method stub
    return null;
  }

}

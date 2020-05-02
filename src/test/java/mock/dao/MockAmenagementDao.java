package mock.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.persistance.dao.AmenagementDao;

import java.util.ArrayList;
import java.util.List;

public class MockAmenagementDao implements AmenagementDao {

  @Inject
  private DtoFactory dtoFactory;

  private List<AmenagementDto> amenagements;



  @Override
  public List<AmenagementDto> listerLesAmenagements() {
    amenagements = new ArrayList<AmenagementDto>();
    for (int i = 0; i < 3; i++) {
      AmenagementDto am = dtoFactory.getAmenagementDto();
      am.setId(i);
      am.setLibelle("test");
      amenagements.add(am);
    }
    return amenagements;
  }

  @Override
  public AmenagementDto getAmenagementById(int id) {
    amenagements = new ArrayList<AmenagementDto>();
    for (int i = 0; i < 3; i++) {
      AmenagementDto am = dtoFactory.getAmenagementDto();
      am.setId(i);
      am.setLibelle("test");
      amenagements.add(am);
    }
    return amenagements.stream().filter(a -> a.getId() == id).findAny().orElse(null);
  }

  @Override
  public AmenagementDto ajouterAmenagment(AmenagementDto amenagement) {
    // TODO Auto-generated method stub
    return amenagement;
  }

}

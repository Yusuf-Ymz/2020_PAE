package be.ipl.pae.bizz.bizz;

import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;

class DtoFactoryImpl implements DtoFactory {

  public UserDto getUserDto() {
    return new User();
  }

  public DevisDto getDevisDto() {
    // TODO Auto-generated method stub
    return new Devis();
  }

  @Override
  public AmenagementDto getAmenagementDto() {

    return new Amenagement();
  }

  @Override
  public ClientDto getClientDto() {
    return new Client();
  }

  @Override
  public PhotoDto getPhotoDto() {
    // TODO Auto-generated method stub
    return new Photo();
  }
}

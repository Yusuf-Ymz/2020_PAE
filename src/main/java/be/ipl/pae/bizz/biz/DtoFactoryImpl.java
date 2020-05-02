package be.ipl.pae.bizz.biz;

import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;

class DtoFactoryImpl implements DtoFactory {
  @Override
  public UserDto getUserDto() {
    return new User();
  }

  @Override
  public DevisDto getDevisDto() {
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
    return new Photo();
  }
}

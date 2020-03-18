package be.ipl.pae.bizz.bizz;

import be.ipl.pae.bizz.dto.DevisDto;
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
}

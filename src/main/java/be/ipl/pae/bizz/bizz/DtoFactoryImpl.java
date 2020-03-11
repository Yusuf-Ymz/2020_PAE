package be.ipl.pae.bizz.bizz;

import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;

class DtoFactoryImpl implements DtoFactory {

  public UserDto getUserDto() {
    return new User();
  }
}

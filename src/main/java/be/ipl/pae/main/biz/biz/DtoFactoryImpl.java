package be.ipl.pae.main.biz.biz;

import be.ipl.pae.main.biz.dto.UserDto;

public class DtoFactoryImpl implements DtoFactory {

  public UserDto getUserDto() {
    return new User();
  }
}

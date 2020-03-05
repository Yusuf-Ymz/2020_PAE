package be.ipl.pae.main.biz.factory;

import be.ipl.pae.main.biz.biz.User;
import be.ipl.pae.main.biz.dto.UserDto;

public class DtoFactoryImpl implements DtoFactory {
  public UserDto getUserDto() {
    return new User();
  }
}

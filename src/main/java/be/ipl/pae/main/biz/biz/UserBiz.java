package be.ipl.pae.main.biz.biz;

import be.ipl.pae.main.biz.dto.UserDto;

public interface UserBiz extends UserDto {
  boolean checkValidePassword(String pwd);
}

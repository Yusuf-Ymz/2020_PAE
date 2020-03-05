package be.ipl.pae.main.biz.biz;

import be.ipl.pae.main.biz.dto.UserDto;

public interface UserBiz extends UserDto {
  /**
   * Si le mot de passe est corret ou pas
   * 
   * @param pwd : le mot de passe a verifié
   * 
   */
  boolean checkValidePassword(String pwd);
}

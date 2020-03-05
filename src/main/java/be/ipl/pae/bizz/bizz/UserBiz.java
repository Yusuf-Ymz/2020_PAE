package be.ipl.pae.bizz.bizz;

import be.ipl.pae.bizz.dto.UserDto;

public interface UserBiz extends UserDto {
  /**
   * Si le mot de passe est corret ou pas
   * 
   * @param pwd : le mot de passe a verifié
   * 
   */
  boolean checkValidePassword(String pwd);
}

package be.ipl.pae.bizz.biz;

import be.ipl.pae.bizz.dto.UserDto;

interface UserBiz extends UserDto {
  /**
   * Si le mot de passe est correct ou pas.
   * 
   * @param pwd : le mot de passe a verifié
   * 
   * @return true si le mot de passe est valide false sinon
   */
  boolean checkValidePassword(String pwd);

}

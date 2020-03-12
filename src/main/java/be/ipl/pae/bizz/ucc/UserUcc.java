package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.UserDto;

public interface UserUcc {
  /**
   * Vérifie si l'utilisateur peut se connecter.
   * 
   * @param pseudo
   * @param password
   * @return un UserDto si le pseudo existe dans la DB null sinon
   */
  UserDto seConnecter(String pseudo, String password);
}

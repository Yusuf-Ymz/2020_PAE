package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.UserDto;



public interface UserDao {
  /**
   * Renvoie un utilisateur existant dans la DataBase.
   * 
   * @param pseudo
   * @return un UserDto
   */
  UserDto obtenirUser(String pseudo);
}

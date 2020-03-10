package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.UserDto;



public interface UserDao {
  UserDto obtenirUser(String pseudo);
}

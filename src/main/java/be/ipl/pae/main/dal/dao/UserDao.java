package be.ipl.pae.main.dal.dao;

import be.ipl.pae.main.biz.dto.UserDto;

public interface UserDao {
  UserDto obtenirUser(String pseudo);
}

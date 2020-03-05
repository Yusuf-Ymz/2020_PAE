package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.UserDto;

public interface UserUcc {
  UserDto seConnecter(String pseudo, String pwd);
}

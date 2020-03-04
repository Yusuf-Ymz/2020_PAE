package be.ipl.pae.main.biz.ucc;

import be.ipl.pae.main.biz.dto.UserDto;

public interface UserUcc {
  UserDto seConnecter(String pseudo, String pwd);
}

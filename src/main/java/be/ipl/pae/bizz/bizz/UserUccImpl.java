package be.ipl.pae.bizz.bizz;

import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.persistance.dao.UserDao;

class UserUccImpl implements UserUcc {

  private UserDao userDao;

  public UserUccImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDto seConnecter(String pseudo, String password) {
    UserDto newUserDto = userDao.obtenirUser(pseudo);
    UserBiz userBiz = (UserBiz) newUserDto;

    if (userBiz == null || !userBiz.checkValidePassword(password) || !userBiz.isConfirme()) {
      return null;
    }
    return newUserDto;
  }

}
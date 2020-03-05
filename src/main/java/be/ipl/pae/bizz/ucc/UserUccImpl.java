package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.bizz.UserBiz;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.persistance.dao.UserDao;

class UserUccImpl implements UserUcc {

  private UserDao userDao;

  public UserUccImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDto seConnecter(UserDto usr) {
    UserDto newUserDto = userDao.obtenirUser(usr);
    UserBiz userBiz = (UserBiz) newUserDto;
    if (newUserDto == null || userBiz.checkValidePassword(usr.getPassword())) {
      return null;
    }

    return newUserDto;
  }

}

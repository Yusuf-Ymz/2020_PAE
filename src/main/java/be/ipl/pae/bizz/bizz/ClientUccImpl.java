package be.ipl.pae.bizz.bizz;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dao.UserDao;

import java.util.List;

public class ClientUccImpl implements ClientUcc {
  private UserDao userDao;

  @Override
  public ClientDto insertClient(ClientDto client, int idOuvrier) {
    UserDto user = userDao.obtenirUserAvecId(idOuvrier);
    if (!user.isOuvrier())
      throw new BizException("Vous n'avez pas les droits");
    return null;
  }

  @Override
  public List<ClientDto> listerClients(int idClient) {
    return null;
  }



}

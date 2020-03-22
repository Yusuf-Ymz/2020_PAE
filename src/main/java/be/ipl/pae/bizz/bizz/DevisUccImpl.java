package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.DevisDao;

import java.util.List;

class DevisUccImpl implements DevisUcc {

  @Inject
  private DevisDao devisdao;
  @Inject
  DalServices dal;

  public List<DevisDto> listerTousLesDevis(int idUser) {
    try {
      dal.startTransaction();
      UserDto user = this.devisdao.obtenirUserAvecId(idUser);
      if (!user.isOuvrier()) {
        return null;
      }
      return this.devisdao.obtenirTousLesDevis();
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }

  public List<DevisDto> listerSesDevis(int idUser) {
    try {
      dal.startTransaction();
      UserDto user = this.devisdao.obtenirUserAvecId(idUser);
      if (user.getClientId() == 0) {
        return null;
      }
      return this.devisdao.obtenirSesDevis(user.getClientId());
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }

}

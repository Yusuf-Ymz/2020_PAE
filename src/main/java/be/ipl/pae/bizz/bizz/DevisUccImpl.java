package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.persistance.dao.DevisDao;

import java.util.List;

class DevisUccImpl implements DevisUcc {

  @Inject
  private DevisDao devisdao;

  public List<DevisDto> listerTousLesDevis(int idUser) {
    // TODO Auto-generated method stub
    UserDto user = this.devisdao.obtenirUserAvecId(idUser);

    if (!user.isOuvrier())
      return null;

    return this.devisdao.obtenirTousLesDevis();
  }

}

package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;

import java.util.List;

public interface DevisDao {
  UserDto obtenirUserAvecId(int idUser);

  List<DevisDto> obtenirTousLesDevis();
}

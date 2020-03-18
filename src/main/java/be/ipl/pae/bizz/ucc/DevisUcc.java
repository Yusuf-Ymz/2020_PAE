package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.DevisDto;

import java.util.List;

public interface DevisUcc {
  List<DevisDto> listerTousLesDevis(int idUser);
}

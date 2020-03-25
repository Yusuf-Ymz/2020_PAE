package be.ipl.pae.bizz.factory;

import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.dto.UserDto;

public interface DtoFactory {

  /**
   * Renvoie une instance d'une classe qui implémente UserDto.
   * 
   * @return un UserDto
   */
  UserDto getUserDto();

  DevisDto getDevisDto();

  AmenagementDto getAmenagementDto();

  ClientDto getClientDto();

  PhotoDto getPhotoDto();
}

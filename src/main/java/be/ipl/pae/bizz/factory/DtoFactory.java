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

  /**
   * Renvoie une instance d'une classe qui implémente DevisDto.
   * 
   * @return un DevisDto
   */
  DevisDto getDevisDto();

  /**
   * Renvoie une instance d'une classe qui implémente AmenagementDto.
   * 
   * @return un AmenagementDto
   */
  AmenagementDto getAmenagementDto();

  /**
   * Renvoie une instance d'une classe qui implémente ClientDto.
   * 
   * @return un ClientDto
   */
  ClientDto getClientDto();

  /**
   * Renvoie une instance d'une classe qui implémente PhotoDto.
   * 
   * @return un PhotoDto
   */
  PhotoDto getPhotoDto();
}

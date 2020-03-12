package be.ipl.pae.bizz.factory;

import be.ipl.pae.bizz.dto.UserDto;

public interface DtoFactory {

  /**
   * Renvoie une instance d'une classe qui implemente UserDto.
   * 
   * @return un UserDto
   */
  UserDto getUserDto();
}

package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.AmenagementDto;

import java.util.List;

public interface AmenagementUcc {

  /**
   * Liste tous les aménagements.
   * 
   * @return la liste des aménagements
   */
  List<AmenagementDto> listerTousLesAmenagements();
}

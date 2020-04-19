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

  /**
   * Insére un aménagement et le renvoie.
   * 
   * @param amenagement : l'aménagement à insérer
   * @return l'aménagement inséré
   */
  AmenagementDto ajouterAmenagement(AmenagementDto amenagement);
}

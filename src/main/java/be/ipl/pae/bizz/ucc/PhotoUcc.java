package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.PhotoDto;

import java.util.List;

public interface PhotoUcc {

  /**
   * Renvoie une liste de photos après aménagements visible.
   * 
   * @return une liste de photo
   */
  List<PhotoDto> listerPhotoCarrousel();

  /**
   * Renvoie une liste de photos après aménagement visible en fonction de l'aménagement
   * séléctionner.
   * 
   * @param idAmenagement : l'id de l'aménagement
   * @return une liste de photo
   */
  List<PhotoDto> listerPhotoParAmenagement(int idAmenagement);

  /**
   * Insére une photo après aménagements.
   * 
   * @param photo : la photo au format base64
   * @param idAmenagement : l'id de l'aménagement
   * @param idDevis : l'id du devis
   * @param visible : si la photo est visble par tous
   * @param preferee : si la photo est celle preférée
   * @return la photo inserer
   */
  PhotoDto insererPhotoApresAmenagement(String photo, int idAmenagement, int idDevis,
      boolean visible, boolean preferee);
}

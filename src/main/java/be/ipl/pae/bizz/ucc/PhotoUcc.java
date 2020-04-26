package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.PhotoDto;

import java.util.List;

public interface PhotoUcc {

  /**
   * Renvoie une liste de photos après aménagement visible.
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

}

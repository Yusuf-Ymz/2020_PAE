package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.PhotoDto;

import java.util.List;

public interface PhotoDao {

  /**
   * Renvoie une liste de photos après aménagement visible.
   * 
   * @return une liste de photo
   */
  List<PhotoDto> recupererLesPhotosVisible();

  /**
   * Renvoie une liste de photos après aménagement visible en fonction de l'aménagement
   * séléctionner.
   * 
   * @param idAmenagement : l'id de l'aménagement
   * @return une liste de photo
   */
  List<PhotoDto> recupererLesPhotosVisibleParAmenagement(int idAmenagement);

  /**
   * Permet d'insérer une photo après aménagement.
   * 
   * @param photo : la photo à insérer
   * @return la photo qui a été insérer
   */
  PhotoDto insererPhotoApresAmenagement(PhotoDto photo);
}

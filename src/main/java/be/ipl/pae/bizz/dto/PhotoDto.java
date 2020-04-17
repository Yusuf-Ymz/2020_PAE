package be.ipl.pae.bizz.dto;

public interface PhotoDto {
  /**
   * Renvoie l'id de la photo.
   * 
   * @return l'id
   */
  int getPhotoId();

  /**
   * Modifie l'id de la photo.
   * 
   * @param photoId : l'id de la photo
   */
  void setPhotoId(int photoId);

  /**
   * Renvoie la photo sous base 64.
   * 
   * @return la photo(base 64)
   */
  String getPhoto();

  /**
   * Modifie la photo.
   * 
   * @param photo : la nouvelle photo
   */
  void setPhoto(String photo);

  /**
   * Renvoie true si la photo est après false si avant.
   * 
   * @return un boolean
   */
  boolean isAvantApres();

  /**
   * Permet de modifier une photo en une photo après ou avant.
   * 
   * @param avantApres : pour mettre avant false sinon true
   */
  void setAvantApres(boolean avantApres);

  /**
   * Renvoie true si la photo est visible faux sinon.
   * 
   * @return un boolean
   */
  boolean isVisible();

  /**
   * Permet de rendre une photo visible ou non.
   * 
   * @param visible : pour rendre visible true sinon false
   */
  void setVisible(boolean visible);

  /**
   * Renvoie l'aménagement lié à la photo.
   * 
   * @return l'aménagement
   */
  AmenagementDto getAmenagement();

  /**
   * Modifie l'aménagement lié à la photo.
   * 
   * @param amenagement : le nouvel aménagement
   */
  void setAmenagement(AmenagementDto amenagement);

  /**
   * Renvoie le devis à qui appartient cette photo.
   * 
   * @return le devis
   */
  DevisDto getDevis();

  /**
   * Modifie le propriètaire de cette photo.
   * 
   * @param devis : le devis qui va posséder cette photo
   */
  void setDevis(DevisDto devis);

  /**
   * Renvoie true si c'est la photo preférée d'un devis false sinon.
   * 
   * @return un boolean
   */
  boolean isPreferee();

  /**
   * Change la valuer de l'attribut.
   * 
   * @param preferee
   */
  void setPreferee(boolean preferee);
}

package be.ipl.pae.bizz.dto;

public interface AmenagementDto {

  /**
   * Renvoie l'id de l'amenagement.
   * 
   * @return id
   */
  int getId();

  /**
   * Modifie l'id de l'amenagement.
   * 
   * @param id : nouvel id de l'amenagement
   */
  void setId(int id);

  /**
   * Renvoie la libelle de l'amenagement.
   * 
   * @return le libelle
   */
  String getLibelle();

  /**
   * Modifie le libelle de l'amenagement.
   * 
   * @param libelle : le nouveau libelle
   */
  void setLibelle(String libelle);

  /**
   * Renvoie le nombre de photos aprés aménagemements visible.
   * 
   * @return le nombre de photos aprés aménagemements visible
   */
  int getNbPhotos();

  void setNbPhotos(int nbPhotos);

}

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
   * @param id
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
   * @param libelle
   */
  void setLibelle(String libelle);

}

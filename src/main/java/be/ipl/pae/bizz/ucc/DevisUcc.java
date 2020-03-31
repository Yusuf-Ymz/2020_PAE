package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.DevisDto;

import java.util.List;

public interface DevisUcc {
  /**
   * Renvoie la liste de tous les devis.
   *
   * 
   * @param idUser : l'utilisateur qui fait la requête
   * @return la liste de tous les devis
   */
  List<DevisDto> listerTousLesDevis();

  /**
   * Renvoie la liste des devis de l'utilisateur.
   * 
   * 
   * @param idUser : l'utilisateur qui fait la requête
   * @return la liste des devis de l'utilisateur
   */
  List<DevisDto> listerSesDevis(int idUser);


  /**
   * Renvoie la liste des devis de l'utilisateur.
   * 
   * 
   * @param idUser : l'utilisateur qui fait la requête
   * @return la liste des devis de l'utilisateur
   */
  List<DevisDto> listerDevisClient(int idClient);

  /**
   * Renvoie le devis correspondant à l'id si l'user est un ouvrier ou le propriétaire du devis.
   * 
   * @param idUser : l'id de l'utilisateur
   * @param idDevis : l'id du devis
   * @return le devis
   */
  DevisDto consulterDevisEnTantQueOuvrier(int idDevis);

  /**
   * Renvoie le devis correspondant à l'id si l'user est un ouvrier ou le propriétaire du devis.
   * 
   * @param idUser : l'id de l'utilisateur
   * @param idDevis : l'id du devis
   * @return le devis
   */
  DevisDto consulterDevisEnTantQueClient(int idUser, int idDevis);


  DevisDto insererDevis(DevisDto devis, int idClient, int[] amenagements, String[] photos);

  /**
   * Cette méthode permet de changer l'état du devis à l'état newEtat
   * 
   * @param idDevis l'id du devis à changer.
   * @param newEtat le nouvel état.
   */
  boolean changerEtatDevis(int idDevis, String newEtat);

}

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
  List<DevisDto> listerTousLesDevis(int idUser);

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
  List<DevisDto> listerDevisClient(int idUser, int idClient);

  /**
   * Renvoie le devis correspondant à l'id si l'user est un ouvrier ou le propriétaire du devis.
   * 
   * @param idUser : l'id de l'utilisateur
   * @param idDevis : l'id du devis
   * @return le devis
   */
  DevisDto consulterDevis(int idUser, int idDevis);

}

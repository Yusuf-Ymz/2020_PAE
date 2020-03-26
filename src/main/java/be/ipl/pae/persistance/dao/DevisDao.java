package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.DevisDto;

import java.util.List;

public interface DevisDao {
  /**
   * Renvoie tous les devis.
   * 
   * @return la liste des devis
   */
  List<DevisDto> obtenirTousLesDevis();

  /**
   * Renvoie les devis appartenant Ã  l'id du client.
   * 
   * @param idClient : l'id du client
   * @return la liste des devis
   */
  List<DevisDto> obtenirSesDevis(int idClient);


  /**
   * Renvoie le devis correspondant Ã  l'id
   * 
   * @param devisId : l'id du devis
   * @return le devis
   */
  DevisDto consulterDevisEnTantQuOuvrier(int devisId);

  /**
   * Renvoie le devis si celui ci appartient Ã  l'utilisateur
   * 
   * @param userId
   * @param devisId
   * @return le devis
   */
  DevisDto consulterDevisEnTantQueUtilisateur(int userId, int devisId);

  void accepterDateTravaux(int numeroDevis);

  /**
   * Permet d'insérer un devis.
   * 
   * @param devis : le devis
   * @param photos : les photos avant aménagement
   * @return le devis
   */
  DevisDto insererDevis(DevisDto devis, String photos[]);
}

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
   * Renvoie les devis appartenant à l'id du client.
   * 
   * @param idClient : l'id du client
   * @return la liste des devis
   */
  List<DevisDto> obtenirSesDevis(int idClient);

  /**
   * Permet d'insérer un devis.
   * 
   * @param idOuvrier : l'id de l'ouvrier
   * @param devis : le devis
   * @return le devis
   */
  DevisDto insererDevis(int idOuvrier, DevisDto devis);

  /**
   * Renvoie le devis correspondant à l'id
   * 
   * @param devisId : l'id du devis
   * @return le devis
   */
  DevisDto consulterDevisEnTantQuOuvrier(int devisId);

  /**
   * Renvoie le devis si celui ci appartient à l'utilisateur
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
   * @param idClient : l'id du client
   * @param devis : le devis
   * @return le devis
   */
  DevisDto insererDevis(DevisDto devis, int idClient, List<String> photos);
}

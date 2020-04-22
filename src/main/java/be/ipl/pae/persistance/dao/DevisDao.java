package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;

import java.util.List;

public interface DevisDao {

  /**
   * Renvoie le devis correspondant à l'id.
   * 
   * @param id : l'id du devis
   * @return le devis correspondant à l'id, null sinon
   */
  DevisDto obtenirDevisById(int id);

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
   * Renvoie le devis correspondant à l'id.
   * 
   * @param devisId : l'id du devis
   * @return le devis
   */
  DevisDto consulterDevisEnTantQuOuvrier(int devisId);

  /**
   * Renvoie le devis si celui ci appartient à l'utilisateur.
   * 
   * @param clientId : l'id du client auquel appartient le devis
   * @param devisId : l'id du devis
   * @return le devis
   */
  DevisDto consulterDevisEnTantQueUtilisateur(int clientId, int devisId);

  /**
   * Permet d'accepter la date de début des travaux.
   * 
   * @param numeroDevis le numéro du devis dont la date doit être acceptée.
   */
  void accepterDateTravaux(int numeroDevis);

  /**
   * Permet d'insérer un devis.
   * 
   * @param devis : le devis
   * @param photos : les photos avant aménagement
   * @return le devis
   */
  DevisDto insererDevis(DevisDto devis, String[] photos);

  /**
   * Cette méthode permet de changer l'état d'un devis
   * 
   * @param idDevis l'id du decis à modifier
   * @param newEtat le nouvel état du devis.
   */
  void changerEtatDevis(int idDevis, String newEtat);

  /**
   * Permet de récupérer l'état actuel du devis.
   * 
   * @param idDevis l'id du devis duquel on doit récupérer l'état.
   * @return l'etat actuel
   */
  String getEtatActuel(int idDevis);

  /**
   * Permet d'insérer une photo après aménagement.
   * 
   * @param photo : la photo à insérer
   * @return la photo qui a été insérer
   */
  PhotoDto insererPhotoApresAmenagement(PhotoDto photo);

  /**
   * Renvoie une liste des tous les clients dont le nom est similaire au paramètre.
   * 
   * @param nom : caractères à match
   * @return liste des noms
   */
  List<String> rechercherNomsClients(String nom);

  /**
   * Renvoie une liste des tous les types d'aménagements de tous les clients dont le libelle est
   * similaire au paramètre.
   * 
   * @param amenagement : caractères à match
   * @return liste des amenagements
   */
  List<String> rechercherAmenagementsTousLesClients(String amenagement);
}

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
   * Renvoie une liste des noms tous les clients dont le nom est similaire au paramètre.
   * 
   * @param nom : caractères à match
   * @return liste des noms
   */
  List<String> rechercherNomsClients(String nom);

  /**
   * Renvoie une liste des prénoms tous les clients dont le nom est similaire au paramètre.
   * 
   * @param prenom : caractères à match
   * @return liste des prénoms
   */
  List<String> rechercherPrenomsClients(String prenom);

  /**
   * Renvoie une liste des tous les types d'aménagements du client dont le libelle est similaire au
   * paramètre.
   * 
   * @param amenagement : caractères à matche
   * @param clientId : le client
   * @return liste des amenagements
   */
  List<String> rechercherAmenagementsDesDevisDUnClient(String amenagement, int clientId);

  /**
   * Renvoie une liste des tous les types d'aménagements de tous les clients dont le libelle est
   * similaire au paramètre.
   * 
   * @param amenagement : caractères à match
   * @return liste des amenagements
   */
  List<String> rechercherAmenagementsDesDevisDeTousLesClients(String amenagement);

  /**
   * Renvoie une liste de devis d'un client dont les attributs sont similaires aux paramètres.
   * 
   * @param clientId : le client
   * @param typeAmenagement : le type d'aménagement
   * @param dateDevis : la date du devis
   * @param montantMin : le montant minimum
   * @param montantMax : le montant maximum
   * @return la liste des devis correspondant
   */
  List<DevisDto> rechercherMesDevisAffine(int clientId, String typeAmenagement, String dateDevis,
      int montantMin, int montantMax);

  /**
   * Renvoie une liste de devis dont les attributs sont similaires aux paramètres.
   * 
   * @param nomClient : le nom du client
   * @param prenomClient : le prenom du client
   * @param typeAmenagement : le type d'aménagement
   * @param dateDevis : la date du devis
   * @param montantMin : le montant minimum
   * @param montantMax : le montant maximum
   * @return la liste des devis correspondant
   */
  List<DevisDto> rechercherTousLesDevisAffine(String nomClient, String prenomClient,
      String typeAmenagement, String dateDevis, int montantMin, int montantMax);

  /**
   * Permet de repousser la date de début des travaux d'un devis.
   * 
   * @param idDevis : le devis
   * @param newDate : la nouvelle date à laquelle va débuter les travaux
   */
  void repousserDateTravaux(int idDevis, String newDate);

  /**
   * Permet d'obtenir la date actuelle du début des traveaux pour un devis.
   *
   * @param idDevis: l'id du devis dont on veut récupérer la date.
   * @return La date du devis sous forme de chaine de caractères.
   * @throws IllegalArgumentException : Si le devis n'est pas trouvé.
   */
  String getDateDebut(int idDevis);
}

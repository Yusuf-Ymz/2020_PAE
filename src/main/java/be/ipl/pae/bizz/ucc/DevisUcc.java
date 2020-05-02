package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;

import java.time.LocalDate;
import java.util.List;

public interface DevisUcc {
  /**
   * Renvoie la liste de tous les devis.
   * 
   * @return la liste de tous les devis
   */
  List<DevisDto> listerTousLesDevis();

  /**
   * Renvoie la liste des devis de l'utilisateur.
   * 
   * 
   * @param idClient : l'utilisateur qui fait la requête
   * @return la liste des devis de l'utilisateur
   */
  List<DevisDto> listerDevisDUnCLient(int idClient);



  /**
   * Renvoie le devis correspondant à l'id si l'user est un ouvrier ou le propriétaire du devis.
   * 
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
  DevisDto consulterDevisEnTantQueUtilisateur(int idUser, int idDevis);

  /**
   * Insére un devis dans la db.
   * 
   * @param devis : le devis à inserer
   * @param idClient : l'id du client auquel on veut lier le devis au client
   * @param amenagements : tableau d'amenagements represantant les travaux du devis
   * @param photos : tableau des photos avant aménagements
   * @return le devis inserer
   */
  DevisDto insererDevis(DevisDto devis, int idClient, int[] amenagements, String[] photos);

  /**
   * Cette méthode permet de changer l'état du devis à l'état newEtat
   * 
   * @param idDevis : l'id du devis à changer.
   * @param newEtat : le nouvel état.
   */
  void changerEtatDevis(int idDevis, String newEtat);

  /**
   * Insére une photo après aménagements.
   * 
   * @param photo : la photo au format base64
   * @param idAmenagement : l'id de l'aménagement
   * @param idDevis : l'id du devis
   * @param visible : si la photo est visble par tous
   * @param preferee : si la photo est celle preférée
   * @return la photo inserer
   */
  PhotoDto insererPhotoApresAmenagement(String photo, int idAmenagement, int idDevis,
      boolean visible, boolean preferee);

  /**
   * Renvoie une liste des noms des clients possédant un devis.
   * 
   * @param nom : le nom du client
   * @return liste des noms
   */
  List<String> listerNomsClients(String nom);

  /**
   * Renvoie une liste des prénoms des clients possédant un devis.
   * 
   * @param prenom : le prénom du client
   * @return liste des prénoms
   */
  List<String> listerPrenomsClients(String prenom);

  /**
   * Renvoie une liste des aménagements qui sont présents dans les devis d'un client.
   * 
   * @param amenagement : le type d'aménagement
   * @param clientId : le client
   * @return liste des aménagements
   */
  List<String> listerAmenagementsRecherches(String amenagement, int clientId);

  /**
   * Renvoie une liste des aménagements qui sont présents dans des devis.
   * 
   * @param amenagement : le type d'aménagement
   * @return liste des aménagements
   */
  List<String> listerAmenagementsTousLesClients(String amenagement);

  /**
   * Renvoie une liste de devis du client dont les attributs sont similaires aux paramètres.
   * 
   * @param clientId : le client
   * @param typeAmenagement : le type d'aménagement
   * @param dateDevis : la date du devis
   * @param montantMin : le montant minimum
   * @param montantMax : le montant maximum
   * @return la liste des devis correspondant
   */
  List<DevisDto> listerMesDevisAffine(int clientId, String typeAmenagement, String dateDevis,
      int montantMin, int montantMax);

  /**
   * Renvoie une liste de devis dont les attributs sont similaires aux paramètres.
   * 
   * @param nomClient : le nom du client
   * @param prenomClient : le prénom du client
   * @param typeAmenagement : le type d'aménagement
   * @param dateDevis : la date du devis
   * @param montantMin : le montant minimum
   * @param montantMax : le montant maximum
   * @return la liste des devis correspondant
   */
  List<DevisDto> listerTousLesDevisAffine(String nomClient, String prenomClient,
      String typeAmenagement, String dateDevis, int montantMin, int montantMax);

  /**
   * Permet de repousser la date de début des travaux d'un devis.
   * 
   * @param devisId : le devis
   * @param date : la nouvelle date à laquelle va débuter les travaux
   */
  void repousserDate(int devisId, LocalDate date);

  /**
   * Permet de confirmer une commande d'aménagement en spécifiant la date de début des travaux.
   * 
   * @param idDevis : l'id du devis
   * @param date : la date de début des travaux
   */
  void confirmerCommandeAmenagement(int idDevis, LocalDate date);


  /**
   * Permet de supprimer la date debut des travaux.
   * 
   * @param idDevis : l'id du devis
   */
  void supprimerDateDebutTravaux(int idDevis);


}

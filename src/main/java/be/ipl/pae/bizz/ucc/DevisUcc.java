package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;

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

}

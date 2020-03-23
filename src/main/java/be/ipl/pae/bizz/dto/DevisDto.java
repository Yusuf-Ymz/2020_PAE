package be.ipl.pae.bizz.dto;

import java.time.LocalDate;
import java.util.List;

public interface DevisDto {
  /**
   * Renvoie l'id du devis.
   * 
   * @return l'id
   */
  int getDevisId();

  /**
   * Modifie l'id du devis.
   * 
   * @param idDevis : l'id du nouveau devis
   */
  void setDevisId(int idDevis);

  /**
   * Renvoie la photo preferee en base 64.
   * 
   * @return la photo
   */
  String getPhotoPreferee();

  /**
   * Modifie la photo preferee.
   * 
   * @param photo : la nouvelle photo
   */
  void setPhotoPreferee(String photo);

  /**
   * Renvoie le client du devis.
   * 
   * @return le client
   */
  ClientDto getClient();

  /**
   * Modifie le client du devis.
   * 
   * @param client : l'id du nouvel client
   */
  void setClient(ClientDto client);

  /**
   * Renvoie la date de début des travaux du devis.
   * 
   * @return la date
   */
  LocalDate getDateDebut();

  /**
   * Modifie la date de début des travaux du devis.
   * 
   * @param date : la nouvelle date
   */
  void setDateDebut(LocalDate date);

  /**
   * Renvoie le montant total du devis.
   * 
   * @return le montant
   */
  int getMontantTotal();

  /**
   * Modifie le montant total du devis.
   * 
   * @param montant : le nouvel montant
   */
  void setMontantTotal(int montant);

  /**
   * Renvoie la durée des travaux du devis.
   * 
   * @return la durée en jour
   */
  int getDuree();

  /**
   * Modifie la durée des travaux du devis.
   * 
   * @param duree : la durée en jour
   */
  void setDuree(int duree);

  /**
   * Renvoie l'état du devis.
   * 
   * @return l'état
   */
  String getEtat();

  /**
   * Modifie l'état du devis.
   * 
   * @param etat : le nouvel état
   */
  void setEtat(String etat);

  /**
   * Renvoie la date d'introduction du devis.
   * 
   * @return la date
   */
  LocalDate getDateDevis();

  /**
   * Modifie la date d'introduction du devis.
   * 
   * @param date : la date
   */
  void setDateDevis(LocalDate date);

  /**
   * Renvoie la liste des aménagements du devis.
   * 
   * @return la liste des aménagements
   */
  List<AmenagementDto> getAmenagements();

  /**
   * Modifie la liste des aménagements du devis.
   * 
   * @param amenagements : la nouvelle liste
   */
  void setAmenagements(List<AmenagementDto> amenagements);
}

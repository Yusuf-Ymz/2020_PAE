package be.ipl.pae.bizz.dto;

import java.time.LocalDate;

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
   * Renvoie l'id du client.
   * 
   * @return l'id
   */
  int getClient();

  /**
   * Modifie l'id du client.
   * 
   * @param idClient : l'id du nouvel client
   */
  void setClient(int idClient);

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
   * @param duree
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
}

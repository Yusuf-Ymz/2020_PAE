package be.ipl.pae.bizz.dto;

import java.time.LocalDate;

public interface DevisDto {
  int getDevisId();

  void setDevisId(int idDevis);

  int getPhotoPreferee();

  void setPhotoPreferee(int idPhoto);

  int getClient();

  void setClient(int idClient);

  LocalDate getDateDebut();

  void setDateDebut(LocalDate date);

  int getMontantTotal();

  void setMontantTotal(int montant);

  int getDuree();

  void setDuree(int duree);

  String getEtat();

  void setEtat(String etat);
}

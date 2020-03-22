package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.FieldDb;

import java.time.LocalDate;

class Devis implements DevisBiz {
  @FieldDb("devis_id")
  private int devisId;

  @FieldDb("photo")
  private String photoPreferee;

  @FieldDb("client")
  private int client;

  @FieldDb("date_debut")
  private LocalDate dateDebut;

  @FieldDb("montant_total")
  private int montantTotal;

  @FieldDb("duree")
  private int duree;

  @FieldDb("etat")
  private String etat;

  @Override
  public int getDevisId() {
    // TODO Auto-generated method stub
    return this.devisId;
  }

  @Override
  public void setDevisId(int idDevis) {
    // TODO Auto-generated method stub
    this.devisId = idDevis;
  }

  @Override
  public String getPhotoPreferee() {
    // TODO Auto-generated method stub
    return this.photoPreferee;
  }

  @Override
  public void setPhotoPreferee(String idPhoto) {
    // TODO Auto-generated method stub
    this.photoPreferee = idPhoto;
  }

  @Override
  public int getClient() {
    // TODO Auto-generated method stub
    return this.client;
  }

  @Override
  public void setClient(int idClient) {
    // TODO Auto-generated method stub
    this.client = idClient;
  }

  @Override
  public LocalDate getDateDebut() {
    // TODO Auto-generated method stub
    return this.dateDebut;
  }

  @Override
  public void setDateDebut(LocalDate date) {
    // TODO Auto-generated method stub
    this.dateDebut = date;
  }

  @Override
  public int getMontantTotal() {
    // TODO Auto-generated method stub
    return this.montantTotal;
  }

  @Override
  public void setMontantTotal(int montant) {
    // TODO Auto-generated method stub
    this.montantTotal = montant;
  }

  @Override
  public int getDuree() {
    // TODO Auto-generated method stub
    return this.duree;
  }

  @Override
  public void setDuree(int duree) {
    // TODO Auto-generated method stub
    this.duree = duree;
  }

  @Override
  public String getEtat() {
    // TODO Auto-generated method stub
    return this.etat;
  }

  @Override
  public void setEtat(String etat) {
    // TODO Auto-generated method stub
    this.etat = etat;
  }

}

package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.FieldDb;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;

import java.time.LocalDate;
import java.util.List;

class Devis implements DevisBiz {
  @FieldDb("devis_id")
  private int devisId;

  @FieldDb("photo")
  private String photoPreferee;

  private ClientDto client;

  @FieldDb("date_debut")
  private LocalDate dateDebut;

  @FieldDb("montant_total")
  private int montantTotal;

  @FieldDb("duree")
  private int duree;

  @FieldDb("etat")
  private String etat;

  @FieldDb("date_devis")
  private LocalDate dateDevis;

  private List<AmenagementDto> amenagements;

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
  public ClientDto getClient() {
    // TODO Auto-generated method stub
    return this.client;
  }

  @Override
  public void setClient(ClientDto client) {
    // TODO Auto-generated method stub
    this.client = client;
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

  @Override
  public LocalDate getDateDevis() {
    // TODO Auto-generated method stub
    return this.dateDevis;
  }

  @Override
  public void setDateDevis(LocalDate date) {
    // TODO Auto-generated method stub
    this.dateDevis = date;
  }

  @Override
  public List<AmenagementDto> getAmenagements() {
    // TODO Auto-generated method stub
    return this.amenagements;
  }

  @Override
  public void setAmenagements(List<AmenagementDto> amenagements) {
    // TODO Auto-generated method stub
    this.amenagements = amenagements;
  }



}

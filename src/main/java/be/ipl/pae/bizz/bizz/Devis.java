package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.FieldDb;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.PhotoDto;

import java.time.LocalDate;
import java.util.List;

class Devis implements DevisBiz {
  @FieldDb("devis_id")
  private int devisId;

  private PhotoDto photoPreferee;

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

  private List<PhotoDto> photosAvants;

  private List<PhotoDto> photosApres;

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
  public PhotoDto getPhotoPreferee() {
    // TODO Auto-generated method stub
    return this.photoPreferee;
  }

  @Override
  public void setPhotoPreferee(PhotoDto photo) {
    // TODO Auto-generated method stub
    this.photoPreferee = photo;
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

  @Override
  public List<PhotoDto> getPhotosAvant() {
    // TODO Auto-generated method stub
    return this.photosAvants;
  }

  @Override
  public void setPhotosAvant(List<PhotoDto> photosAvant) {
    // TODO Auto-generated method stub
    this.photosAvants = photosAvant;
  }

  @Override
  public List<PhotoDto> getPhotosApres() {
    // TODO Auto-generated method stub
    return this.photosApres;
  }

  @Override
  public void setPhotoApres(List<PhotoDto> photoApres) {
    // TODO Auto-generated method stub
    this.photosApres = photoApres;
  }



}

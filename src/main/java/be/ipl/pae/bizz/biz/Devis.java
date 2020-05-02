package be.ipl.pae.bizz.biz;

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

  private Etat etat;

  @FieldDb("date_devis")
  private LocalDate dateDevis;

  private List<AmenagementDto> amenagements;

  private List<PhotoDto> photosAvants;

  private List<PhotoDto> photosApres;

  @Override
  public int getDevisId() {
    return this.devisId;
  }

  @Override
  public void setDevisId(int idDevis) {

    this.devisId = idDevis;
  }

  @Override
  public PhotoDto getPhotoPreferee() {

    return this.photoPreferee;
  }

  @Override
  public void setPhotoPreferee(PhotoDto photo) {

    this.photoPreferee = photo;
  }

  @Override
  public ClientDto getClient() {

    return this.client;
  }

  @Override
  public void setClient(ClientDto client) {

    this.client = client;
  }

  @Override
  public LocalDate getDateDebut() {

    return this.dateDebut;
  }

  @Override
  public void setDateDebut(LocalDate date) {

    this.dateDebut = date;
  }

  @Override
  public int getMontantTotal() {

    return this.montantTotal;
  }

  @Override
  public void setMontantTotal(int montant) {

    this.montantTotal = montant;
  }

  @Override
  public int getDuree() {

    return this.duree;
  }

  @Override
  public void setDuree(int duree) {
    // TODO Auto-generated method stub
    this.duree = duree;
  }

  @Override
  public String getEtat() {

    return this.etat.getEtat();
  }

  @Override
  public void setEtat(String etat) {

    switch (etat) {
      case "Devis introduit":
        this.etat = Etat.INTRODUIT;
        break;
      case "Annulé":
        this.etat = Etat.ANNULE;
        break;
      case "Commande confirmée":
        this.etat = Etat.COMMANDE_CONFIRMEE;
        break;
      case "Acompte payé":
        this.etat = Etat.ACOMPTE_PAYE;
        break;
      case "Facture de milieu chantier envoyée":
        this.etat = Etat.FACTURE_MILIEU_CHANTIER;
        break;
      case "Visible":
        this.etat = Etat.VISIBLE;
        break;
      case "Facture de fin de chantier envoyée":
        this.etat = Etat.FACTURE_FIN_CHANTIER;
        break;
      default:
        this.etat = Etat.NO_ETAT;
        break;
    }
  }

  @Override
  public LocalDate getDateDevis() {

    return this.dateDevis;
  }

  @Override
  public void setDateDevis(LocalDate date) {

    this.dateDevis = date;
  }

  @Override
  public List<AmenagementDto> getAmenagements() {

    return this.amenagements;
  }

  @Override
  public void setAmenagements(List<AmenagementDto> amenagements) {

    this.amenagements = amenagements;
  }

  @Override
  public List<PhotoDto> getPhotosAvant() {

    return this.photosAvants;
  }

  @Override
  public void setPhotosAvant(List<PhotoDto> photosAvant) {

    this.photosAvants = photosAvant;
  }

  @Override
  public List<PhotoDto> getPhotosApres() {
    return this.photosApres;
  }

  @Override
  public void setPhotoApres(List<PhotoDto> photoApres) {
    this.photosApres = photoApres;
  }



}

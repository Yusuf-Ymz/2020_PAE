package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.FieldDb;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.DevisDto;

class Photo implements PhotoBiz {
  @FieldDb("photo_id")
  private int photoId;
  @FieldDb("photo")
  private String photo;
  @FieldDb("avant_apres")
  private boolean avantApres;
  @FieldDb("visible")
  private boolean visible;

  private boolean preferee;

  private AmenagementDto amenagement;

  private DevisDto devis;


  @Override
  public int getPhotoId() {
    // TODO Auto-generated method stub
    return photoId;
  }

  @Override
  public void setPhotoId(int photoId) {
    // TODO Auto-generated method stub
    this.photoId = photoId;
  }

  @Override
  public String getPhoto() {
    // TODO Auto-generated method stub
    return this.photo;
  }

  @Override
  public void setPhoto(String photo) {
    // TODO Auto-generated method stub
    this.photo = photo;
  }

  @Override
  public boolean isAvantApres() {
    // TODO Auto-generated method stub
    return this.avantApres;
  }

  @Override
  public void setAvantApres(boolean avantApres) {
    // TODO Auto-generated method stub
    this.avantApres = avantApres;
  }

  @Override
  public boolean isVisible() {
    // TODO Auto-generated method stub
    return this.visible;
  }

  @Override
  public void setVisible(boolean visible) {
    // TODO Auto-generated method stub
    this.visible = visible;
  }

  @Override
  public AmenagementDto getAmenagement() {
    // TODO Auto-generated method stub
    return this.amenagement;
  }

  @Override
  public void setAmenagement(AmenagementDto amenagement) {
    // TODO Auto-generated method stub
    this.amenagement = amenagement;
  }

  @Override
  public DevisDto getDevis() {
    // TODO Auto-generated method stub
    return this.devis;
  }

  @Override
  public void setDevis(DevisDto devis) {
    // TODO Auto-generated method stub
    this.devis = devis;
  }

  @Override
  public boolean isPreferee() {
    return preferee;
  }

  @Override
  public void setPreferee(boolean preferee) {
    this.preferee = preferee;
  }

}

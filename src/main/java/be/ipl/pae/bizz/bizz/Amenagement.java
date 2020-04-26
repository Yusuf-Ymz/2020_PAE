package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.FieldDb;

class Amenagement implements AmenagementBiz {

  @FieldDb("type_amenagement")
  private int id;
  @FieldDb("libelle")
  private String libelle;

  private int nbPhotos;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;

  }

  @Override
  public String getLibelle() {

    return libelle;
  }

  @Override
  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

  public int getNbPhotos() {
    return nbPhotos;
  }

  public void setNbPhotos(int nbPhotos) {
    this.nbPhotos = nbPhotos;
  }

}

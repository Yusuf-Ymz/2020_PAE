package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.FieldDb;

class Amenagement implements AmenagementBiz {

  @FieldDb("type_amenagement")
  int id;
  @FieldDb("libelle")
  String libelle;



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

}

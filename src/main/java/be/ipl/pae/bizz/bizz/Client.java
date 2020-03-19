package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.FieldDb;

class Client implements ClientBiz {

  @FieldDb("devis_id")
  private int idDevis;

  @FieldDb("nom")
  private String nom;

  @FieldDb("prenom")
  private String prenom;

  @FieldDb("rue")
  private String rue;

  @FieldDb("numero")
  private int numero;

  @FieldDb("boite")
  private String boite;

  @FieldDb("ville")
  private String ville;

  @FieldDb("code_postal")
  private String codePostal;

  @FieldDb("email")
  private String email;

  @FieldDb("telephone")
  private String telephone;

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  @Override
  public String getPrenom() {
    return prenom;
  }

  @Override
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  @Override
  public String getRue() {
    return rue;
  }

  @Override
  public void setRue(String rue) {
    this.rue = rue;
  }

  @Override
  public int getNumero() {
    return numero;
  }

  @Override
  public void setNumero(int numero) {
    this.numero = numero;
  }

  @Override
  public String getBoite() {
    return boite;
  }

  @Override
  public void setBoite(String boite) {
    this.boite = boite;
  }

  @Override
  public String getVille() {
    return ville;
  }

  @Override
  public void setVille(String ville) {
    this.ville = ville;
  }

  @Override
  public String getCodePostal() {
    return codePostal;
  }

  @Override
  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getTelephone() {
    return telephone;
  }

  @Override
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @Override
  public int getIdDevis() {
    return idDevis;
  }

  @Override
  public void setIdDevis(int idDevis) {
    this.idDevis = idDevis;
  }


}

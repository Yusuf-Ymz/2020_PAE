package be.ipl.pae.bizz.biz;

import be.ipl.pae.annotation.FieldDb;

import org.mindrot.bcrypt.BCrypt;

import java.time.LocalDate;

class User implements UserBiz {

  @FieldDb("pseudo")
  private String pseudo;

  @FieldDb("nom")
  private String nom;

  @FieldDb("prenom")
  private String prenom;

  @FieldDb("email")
  private String email;

  @FieldDb("ville")
  private String ville;

  @FieldDb("ouvrier")
  private boolean ouvrier;

  @FieldDb("confirme")
  private boolean confirme;

  @FieldDb("date_inscription")
  private LocalDate dateInscription;

  @FieldDb("mot_de_passe")
  private String password;

  @FieldDb("utilisateur_id")
  private int userId;

  @FieldDb("client_id")
  private int clientId;

  public User() {
    dateInscription = LocalDate.now();
  }


  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }


  public String getNom() {
    return nom;
  }


  public void setNom(String nom) {
    this.nom = nom;
  }


  public String getPrenom() {
    return prenom;
  }


  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }


  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public String getVille() {
    return ville;
  }


  public void setVille(String ville) {
    this.ville = ville;
  }


  public boolean isOuvrier() {
    return ouvrier;
  }


  public void setOuvrier(boolean ouvrier) {
    this.ouvrier = ouvrier;
  }


  public boolean isConfirme() {
    return confirme;
  }


  public void setConfirme(boolean confirme) {
    this.confirme = confirme;
  }

  public LocalDate getDateInscription() {
    return dateInscription;
  }


  public void setDateInscription(LocalDate dateInscription) {
    this.dateInscription = dateInscription;
  }


  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
  }


  public int getUserId() {
    return userId;
  }


  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getClientId() {
    return this.clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  @Override
  public boolean checkValidePassword(String pwd) {
    return BCrypt.checkpw(pwd, this.password);
  }

  @Override
  public String toString() {
    return "User [pseudo=" + pseudo + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email
        + ", ville=" + ville + ", ouvrier=" + ouvrier + ", confirme=" + confirme
        + ", dateInscription=" + dateInscription + ", password=" + password + ", userId=" + userId
        + ", clientId=" + clientId + "]";
  }

}

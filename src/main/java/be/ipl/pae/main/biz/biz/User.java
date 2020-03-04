package be.ipl.pae.main.biz.biz;

import java.time.LocalDate;
import org.mindrot.bcrypt.BCrypt;

public class User implements UserBiz {

  private String pseudo;
  private String nom;
  private String prenom;
  private String email;
  private String ville;
  private boolean ouvrier;
  private boolean confirme;
  private LocalDate dateInscription;
  private String password;
  private int userId;

  /**
   * 
   */
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
    this.password = password;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public boolean checkValidePassword(String pwd) {
    return BCrypt.checkpw(pwd, this.password);
  }



}

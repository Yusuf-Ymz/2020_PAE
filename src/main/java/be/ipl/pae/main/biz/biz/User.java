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
   * Creer un nouvel utilisateur
   */
  public User() {
    dateInscription = LocalDate.now();
  }

  /**
   * renvoie le pseudo de l'utilisateur
   * 
   * @return pseudo : le pseudo de l'utilisateur
   */
  public String getPseudo() {
    return pseudo;
  }

  /**
   * Modifie le pseudo de l'utilisateur
   * 
   * @param pseudo : la nouvelle valeur du pseudo
   */
  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  /**
   * renvoie le nom de l'utilisateur
   * 
   * @return nom : nom de l'utilisateur
   */
  public String getNom() {
    return nom;
  }

  /**
   * Modifie le nom de l'utilisateur
   * 
   * @param nom : la nouvelle valeur du nom
   */
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * renvoie le prenom de l'utilisateur
   * 
   * @return prenom : le prenom de l'utilisateur
   */
  public String getPrenom() {
    return prenom;
  }

  /**
   * Modifie le prenom de l'utilisateur
   * 
   * @param prenom :la nouvelle valeur du prenom
   */
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  /**
   * Renvoie le mail de l'utilisateur
   * 
   * @return email : le mail de l'utilisateur
   * 
   */
  public String getEmail() {
    return email;
  }

  /**
   * Modifie le mail de l'utilisateur
   * 
   * @param email : la nouvelle valeur du mail
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Renvoie la ville de l'utilisateur
   * 
   * @return ville : la ville de l'utilisateur
   */
  public String getVille() {
    return ville;
  }

  /**
   * Modifie la ville de l'utilisateur
   * 
   * @param ville :la nouvelle valeur de la ville
   */
  public void setVille(String ville) {
    this.ville = ville;
  }

  /**
   * renvoie si l'utlisateur est un ouvrier
   * 
   *
   */
  public boolean isOuvrier() {
    return ouvrier;
  }

  /**
   * Modifie le boolean de ouvrier
   * 
   * @param ouvrier: le boolean a modifié
   */
  public void setOuvrier(boolean ouvrier) {
    this.ouvrier = ouvrier;
  }

  /**
   * Renvoie si l'utilisateur est confirmé
   * 
   * @return confirmer : le boolean
   */
  public boolean isConfirme() {
    return confirme;
  }

  /**
   * Modifier le boolean confirme
   * 
   * @param confimer : la nouvelle valeur
   */
  public void setConfirme(boolean confirme) {
    this.confirme = confirme;
  }

  /**
   * renvoie la date d'inscription
   * 
   * @return dateInscription : la date d'inscription
   */
  public LocalDate getDateInscription() {
    return dateInscription;
  }

  /**
   * Modifie la date d'inscription
   * 
   * @param dateInscription : la nouvelle date
   */
  public void setDateInscription(LocalDate dateInscription) {
    this.dateInscription = dateInscription;
  }

  /**
   * renvoie le mot de passe de l'utilisateur
   * 
   * @return password : le mot de passe
   */
  public String getPassword() {
    return password;
  }

  /**
   * Modifie le password de l'utilisateur
   * 
   * @param password : le nouveau password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * renvoie l'id de l'utilisateur
   * 
   * @return userId : l'id de l'utilisateur
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Modifie l'id de l'utilisateur
   * 
   * @param userId : le nouvelle id
   * 
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public boolean checkValidePassword(String pwd) {
    return BCrypt.checkpw(pwd, this.password);
  }



}

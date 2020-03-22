package be.ipl.pae.bizz.dto;

import java.time.LocalDate;

public interface UserDto {
  /**
   * Renvoie le pseudo du user.
   * 
   * @return le pseudo
   */
  String getPseudo();

  /**
   * Modifie l'attribut pseudo.
   * 
   * @param pseudo : le pseudo de l'utilisateur
   */
  void setPseudo(String pseudo);

  /**
   * Renvoie le nom de l'utilisateur.
   * 
   * @return le nom
   */
  String getNom();

  /**
   * Modifie le nom de l'utilisateur.
   * 
   * @param nom : le nom de l'utilisateur
   */
  void setNom(String nom);

  /**
   * Renvoie le prénom de l'utilisateur.
   * 
   * @return le prénom
   */
  String getPrenom();

  /**
   * Modifie le prénom de l'utilisateur.
   * 
   * @param prenom : le prenom de l'utilisateur
   */
  void setPrenom(String prenom);

  /**
   * Renvoie l'email de l'utilisateur.
   * 
   * @return l'email
   */
  String getEmail();

  /**
   * Modifie l'email de l'utilisateur.
   * 
   * @param email : l'email de l'utilisateur
   */
  void setEmail(String email);

  /**
   * Renvoie la ville de résidence de l'utilisateur.
   * 
   * @return la ville
   */
  String getVille();

  /**
   * Modifie la ville de résidence de l'utilisateur.
   * 
   * @param ville : la ville de l'utilisateur
   */
  void setVille(String ville);

  /**
   * Renvoie true si c'est un ouvrier false sinon.
   * 
   * @return un boolean
   */
  boolean isOuvrier();

  /**
   * Modifie le statut de l'utilisateur.
   * 
   * @param ouvrier : la nouvelle valeur
   */
  void setOuvrier(boolean ouvrier);

  /**
   * Renvoie true si l'utilisateur a été confirmé false sinon.
   * 
   * @return un boolean
   */
  boolean isConfirme();

  /**
   * Modifier le boolean confirme.
   * 
   * @param confirme : la nouvelle valeur
   */
  void setConfirme(boolean confirme);

  /**
   * Renvoie la date d'inscription.
   * 
   * @return dateInscription : la date d'inscription
   */
  LocalDate getDateInscription();

  /**
   * Modifie la date d'inscription.
   * 
   * @param dateInscription : la nouvelle date
   */

  void setDateInscription(LocalDate dateInscription);

  /**
   * Renvoie le mot de passe de l'utilisateur.
   * 
   * @return password : le mot de passe
   */
  String getPassword();

  /**
   * Modifie le password de l'utilisateur.
   * 
   * @param password : le nouveau password
   */
  void setPassword(String password);

  /**
   * Renvoie l'id de l'utilisateur.
   * 
   * @return userId : l'id de l'utilisateur
   */
  int getUserId();

  /**
   * Modifie l'id de l'utilisateur.
   * 
   * @param userId : le nouvel id
   * 
   */
  void setUserId(int userId);

  /**
   * Renvoie l'id du client associé à l'utilisateur.
   * 
   * @return clientId : l'id du client
   */
  int getClientId();

  /**
   * Modifie l'id du client associé à l'utilisateur.
   * 
   * @param clientId : le nouvel id
   */
  void setClientId(int clientId);
}

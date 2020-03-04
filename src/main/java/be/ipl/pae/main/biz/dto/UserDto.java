package be.ipl.pae.main.biz.dto;

import java.time.LocalDate;

public interface UserDto {
  String getPseudo();

  void setPseudo(String pseudo);

  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getEmail();

  void setEmail(String email);

  String getVille();

  void setVille(String ville);

  boolean isOuvrier();

  void setOuvrier(boolean ouvrier);

  boolean isConfirme();

  void setConfirme(boolean confirme);

  LocalDate getDateInscription();

  void setDateInscription(LocalDate dateInscription);

  String getPassword();

  void setPassword(String password);

  int getUserId();

  void setUserId(int userId);

  // int getClientId();

  // void setClientId(int clientId);
}

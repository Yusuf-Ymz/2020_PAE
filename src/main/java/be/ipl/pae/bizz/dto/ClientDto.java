package be.ipl.pae.bizz.dto;

public interface ClientDto {
  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getRue();

  void setRue(String rue);

  int getNumero();

  void setNumero(int numero);

  String getBoite();

  void setBoite(String boite);

  String getVille();

  void setVille(String ville);

  String getCodePostal();

  void setCodePostal(String codePostal);

  String getEmail();

  void setEmail(String email);

  String getTelephone();

  void setTelephone(String telephone);

  int getIdDevis();

  void setIdDevis(int idDevis);
}

package be.ipl.pae.bizz.dto;

public interface ClientDto {
  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getRue();

  void setRue(String rue);

  String getNumero();

  void setNumero(String numero);

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

  int getIdClient();

  void setIdClient(int idClient);
}

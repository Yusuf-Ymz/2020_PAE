package be.ipl.pae.bizz.dto;

public interface ClientDto {

  /**
   * Renvoie le nom du client.
   * 
   * @return le nom
   */
  String getNom();

  /**
   * Modifie le nom du client.
   * 
   * @param nom : le nouveau nom du client
   */
  void setNom(String nom);

  /**
   * Renvoie le prénom du client.
   * 
   * @return le prenom
   */
  String getPrenom();

  /**
   * Modifie le prénom du client.
   * 
   * @param prenom : le nouveau prénom du client
   */
  void setPrenom(String prenom);

  /**
   * Renvoie la rue du client.
   * 
   * @return la rue du client
   */
  String getRue();

  /**
   * Modifie la rue de client.
   * 
   * @param rue : la nouvelle rue
   */
  void setRue(String rue);

  /**
   * Renvoie le numero de porte du client.
   * 
   * @return le numero de porte
   */
  String getNumero();

  /**
   * Modifie le numero de porte du client.
   * 
   * @param numero : le nouveau porte du client
   */
  void setNumero(String numero);


  /**
   * Renvoie la ville du client.
   * 
   * @return la ville du client
   */
  String getVille();

  /**
   * Modifie la ville du client.
   * 
   * @param ville : la nouvelle ville du client
   */
  void setVille(String ville);

  /**
   * Renvoie le code postal du client.
   * 
   * @return le code postal du client
   */
  String getCodePostal();

  /**
   * Modifie le code postal du client.
   * 
   * @param codePostal : le nouveau code postal client
   */
  void setCodePostal(String codePostal);

  /**
   * Renvoie l'email du client.
   * 
   * @return l'email du client
   */
  String getEmail();

  /**
   * Modifie l'email du client.
   * 
   * @param email : le nouvel email du client
   */
  void setEmail(String email);

  /**
   * Renvoie le numero de télephone du client.
   * 
   * @return le numero de télephone du client
   */
  String getTelephone();

  /**
   * Modifie le numero de télephone du client.
   * 
   * @param telephone : le nouveau numero de télephone
   */
  void setTelephone(String telephone);

  /**
   * Renvoie l'id du client.
   * 
   * @return l'id du client
   */
  int getIdClient();

  /**
   * Modifie l'id du client.
   * 
   * @param idClient : le nouvel id du client
   */
  void setIdClient(int idClient);
}

package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.UserDto;

import java.util.List;



public interface UserDao {
  /**
   * Renvoie un utilisateur existant dans la DataBase.
   * 
   * @param pseudo : un pseudo
   * @return un UserDto
   */
  UserDto obtenirUser(String pseudo);


  /**
   * Renvoie la liste de tous les utilisateurs inscrits de la DataBase.
   * 
   * @return une liste de tous les utilisteurs ou null s'il n'y a pas d'utilisateur
   */
  List<UserDto> obtenirListeUser();

  /**
   * Renvoie la liste de tous les utilisateurs non confirmé de la DataBase.
   * 
   * @return une liste de tous les utilisteurs non confirmé ou null s'il n'y a pas d'utilisateur
   */
  List<UserDto> obtenirListeUsersPreInscrit();

  /**
   * Renvoie l'utilisateur dont l'id est passer en parametre.
   * 
   * @param id : l'id de l'utilisateur rechercher
   * @return un userDto
   */
  UserDto obtenirUserAvecId(int id);

  /**
   * Modifie les droits de l'utilisateur dont l'id est passé en parametre en mettant la valeur de
   * l'attribut "confirme" et la valeur de l'attribut ouvrier à true.
   * 
   * @param newOuvrierId : l'id de l'utilisateur
   * @return l'utilisateur modifié
   */
  UserDto addConfirmWorkerWithId(int newOuvrierId);

  /**
   * Insert en DB l'utilisateur passé en paramètre.
   * 
   * @param user : l'utilisateur
   */
  void inscrireUser(UserDto user);

  /**
   * Vérifie si le pseudo passé en paramètre exist.
   * 
   * @param pseudo : pseudo à vérifier
   * @return true si le pseudo exist sinon false.
   */
  boolean pseudoExiste(String pseudo);

  /**
   * Vérifie si l'email passé en paramètre exist.
   * 
   * @param pseudo : email à vérifier
   * @return true si l'email exist sinon false.
   */
  boolean emailExiste(String email);

  /**
   * Modifie les droits de l'utilisateur dont l'idUser est passé en parametre en mettant la valeur
   * de l'attribut "confirme" à true et l'attribut "client_id" égale à l'idClient.
   * 
   * @param idUser : id de l'utilisateur
   * @param idClient : id du client
   * @return l'utilisateur modifié
   */
  UserDto addUtilisateurClient(int idUser, int idClient);
}

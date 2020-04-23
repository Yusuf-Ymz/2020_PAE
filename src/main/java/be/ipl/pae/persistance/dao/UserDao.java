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
   * @param email : email à vérifier
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

  /**
   * Renvoie une liste des noms des utilisateurs similaire au paramètre.
   * 
   * @param nom : le nom à matcher
   * @return la liste des noms
   */
  List<String> rechercherNomsUtilisateurs(String nom);

  /**
   * Renvoie une liste des prénoms des utilisateurs similaire au paramètre.
   * 
   * @param prenom : le prénom à matcher
   * @return la liste des prénoms
   */
  List<String> rechercherPrenomsUtilisateurs(String prenom);

  /**
   * Renvoie une liste des villes des utilisateurs similaire au paramètre.
   * 
   * @param ville : la ville à matcher
   * @return la liste des villes
   */
  List<String> rechercherVillesUtilisateurs(String ville);

  /**
   * Renvoie une liste contenant les utilisateurs dont les paramètre sont similaires aux attributs.
   * 
   * @param nom : le nom à matcher
   * @param prenom : le prénom à matcher
   * @param ville : la ville à matcher
   * @return la lister des utilisateurs
   */
  List<UserDto> rechercherUtilisateurs(String nom, String prenom, String ville);

  /**
   * Renvoie une liste des noms des utilisateurs non confirmé similaire au paramètre.
   * 
   * @param nom : le nom à matcher
   * @return la liste des noms
   */
  List<String> rechercherNomsUtilisateursNonConfirme(String nom);

  /**
   * Renvoie une liste des prénoms des utilisateurs non confirmé similaire au paramètre.
   * 
   * @param prenom : le prénom à matcher
   * @return la liste des prénoms
   */
  List<String> rechercherPrenomsUtilisateursNonConfirme(String prenom);

  /**
   * Renvoie une liste des villes des utilisateurs non confirmé similaire au paramètre.
   * 
   * @param ville : la ville à matcher
   * @return la liste des villes
   */
  List<String> rechercherVillesUtilisateursNonConfirme(String ville);

  /**
   * Renvoie une liste contenant les utilisateurs non confirmé dont les paramètre sont similaires.
   * aux attributs.
   * 
   * @param nom : le nom à matcher
   * @param prenom : le prénom à matcher
   * @param ville : la ville à matcher
   * @return la lister des utilisateurs
   */
  List<UserDto> rechercherUtilisateurNonConfirme(String nom, String prenom, String ville);


}

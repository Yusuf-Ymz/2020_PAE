package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.UserDto;

import java.util.List;

public interface UserUcc {
  /**
   * Vérifie si l'utilisateur peut se connecter.
   * 
   * @param pseudo : le pseudo de la personne qui veut se connecter
   * @param password : son mot de passe
   * @return un UserDto si le pseudo existe dans la DB null sinon
   */
  UserDto seConnecter(String pseudo, String password);

  /**
   * Permet l'inscription de l'utilisateur.
   * 
   * @param user : l'utilisateur à inscrire
   */
  void inscrire(UserDto user);

  /**
   * Renvoie la liste des utilisateurs.
   * 
   * 
   * @return renvoie une liste des utilisateurs ou null si pas d'utilisateurs
   */
  List<UserDto> listerUsers();

  /**
   * Renvoie la liste des utilisateurs qui ne sont pas encore confirmé.
   * 
   * 
   * @return renvoie une liste des utilisateurs confimrés ou null si pas d'utilisateurs confirmés
   */
  List<UserDto> listerUsersPreinscrit();

  /**
   * Lie un utilisateur a un client et confirme l'utilisateur.
   * 
   * 
   * @param idUser : id de l'utilisateur à lier
   * @param idClient : id du client lié à l'utilisateur
   * @return renvoie l'utilisateurs confirmés et lié
   */
  UserDto confirmUser(int idUser, int idClient);

  /**
   * Permet à l'utilisateur non confirmé en paramètre de devenir un ouvrier confirmé.
   * 
   * 
   * @param userId : id de l'utilisateur non confirmé
   * @return renvoie l'utilisateur devenu ouvrier
   */
  UserDto confirmWorker(int userId);

  /**
   * Vas chercher l'utilisateur avec le même id que le paramètre.
   * 
   * 
   * @param userId : id de l'utilisateur
   * @return renvoie l'utilisateur
   */
  UserDto obtenirUtilisateur(int userId);

  /**
   * Renvoie une liste contenant les noms des utilisateurs similaire au paramètre.
   * 
   * @param nom : nom à matcher
   * @return la liste des noms
   */
  List<String> listerNomsUtilisateurs(String nom);

  /**
   * Renvoie une liste contenant les prénoms des utilisateurs similaire au paramètre.
   * 
   * @param prenom : prénom à matcher
   * @return la liste des prénoms
   */
  List<String> listerPrenomsUtilisateurs(String prenom);

  /**
   * Renvoie une liste contenant les villes des utilisateurs similaire au paramètre.
   * 
   * @param ville : la ville à matcher
   * @return la liste des villes
   */
  List<String> listerVillesUtilisateurs(String ville);

  /**
   * Renvoie une liste contenant les utilisateurs dont les paramètre sont similaires aux attributs.
   * 
   * @param nom : le nom à matcher
   * @param prenom : le prénom à matcher
   * @param ville : la ville à matcher
   * @return la lister des utilisateurs
   */
  List<UserDto> listerUtilisateursAvecCriteres(String nom, String prenom, String ville);
}

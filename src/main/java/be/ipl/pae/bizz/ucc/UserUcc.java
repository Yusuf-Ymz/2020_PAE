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
   * Lie un utilisateur a un client et confirme l'utilisateur
   * 
   * 
   * @param idUser : id de l'utilisateur à lier
   * @param idClient : id du client lié à l'utilisateur
   * @return renvoie l'utilisateurs confirmés et lié
   */
  UserDto confirmUser(int idUser, int idClient);

  /**
   * Permet à l'utilisateur non confirmé en paramètre de devenir un ouvrier confirmé
   * 
   * 
   * @param userId : id de l'utilisateur non confirmé
   * @return renvoie l'utilisateur devenu ouvrier
   */
  UserDto confirmWorker(int userId);

  /**
   * Vas chercher l'utilisateur avec le même id que le paramètre
   * 
   * 
   * @param userId : id de l'utilisateur
   * @return renvoie l'utilisateur
   */
  UserDto obtenirUtilisateur(int userId);


}

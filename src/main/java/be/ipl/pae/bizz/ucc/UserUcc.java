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
   * Renvoie la liste des utilisateurs.
   * 
   * @param userId
   * 
   * @return renvoie une liste des utilisateurs ou null si pas d'utilisateurs
   */
  List<UserDto> listerUsers(int userId);

  List<UserDto> listerUsersPreinscrit(int userId);


  /**
   * Revoie l'utilisateur dont l'id est passer en parametre.
   * 
   * @param id : l'id de l'utilisateur rechercher
   * @return un userDto
   */
  UserDto obtenirUser(int id);
}

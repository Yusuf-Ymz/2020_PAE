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


  List<UserDto> obtenirListeUsersPreInscrit();



  /**
   * Renvoie l'utilisateur dont l'id est passer en parametre.
   * 
   * @param id : l'id de l'utilisateur rechercher
   * @return un userDto
   */
  UserDto obtenirUserAvecId(int id);


  void addConfirmWorkerWithId(int idConfirmed);


  void inscrireUser(UserDto user);

  boolean pseudoExiste(String pseudo);

  boolean emailExiste(String email);


  void addUtilisateurClient(int idUser, int idClient);
}

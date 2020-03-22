package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;

import java.util.List;

public interface DevisDao {
  /**
   * Renvoie l'utilisateur correspondant à l'id
   * 
   * @param idUser : l'id
   * @return l'utilisateur
   */
  UserDto obtenirUserAvecId(int idUser);

  /**
   * Renvoie tous les devis
   * 
   * @return la liste des devis
   */
  List<DevisDto> obtenirTousLesDevis();

  /**
   * Renvoie les devis appartenant à l'id du client
   * 
   * @param idClient
   * @return la liste des devis
   */
  List<DevisDto> obtenirSesDevis(int idClient);

  /**
   * Permet d'insérer un devis
   * 
   * @param idOuvrier : l'id de l'ouvrier
   * @param devis : le devis
   * @return le devis
   */
  DevisDto insererDevis(int idOuvrier, DevisDto devis);

}

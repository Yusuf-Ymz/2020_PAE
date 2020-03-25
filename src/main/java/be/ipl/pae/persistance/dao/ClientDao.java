package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.UserDto;

import java.util.List;

public interface ClientDao {

  /**
   * Insere un nouveau client dans la DB.
   * 
   * @param client : le client à inserer
   * @return le client inserer ou null si pas inserer
   */
  ClientDto insererClient(ClientDto client);

  /**
   * Liste tous les clients.
   * 
   * @return la liste des clients
   */
  List<ClientDto> listerClients();


  List<ClientDto> rechercherClients(String ville, String codePostal, String nomClient,
      String prenomClient);

  List<String> rechercherVilles(String ville);

  List<String> rechercheCodePostaux(String codePostal);

  List<String> rechercherPrenoms(String prenom);

  List<String> rechercherNoms(String nom);

  ClientDto getClientById(int id);

  List<ClientDto> rechercherClientsPasUtilisateur();

  UserDto rechercherClientAvecId(int idClient);
}

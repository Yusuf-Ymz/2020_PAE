package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.ClientDto;

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

  /**
   * Liste tous les client qui ont comme "nom","prenom","ville" et "code_postale" les paramètres
   * suivant nomClient,prenomClient,ville et codePostal.
   * 
   * @param nomClient : nom du client
   * @param prenomClient : prenom du client
   * @param ville : ville du client
   * @param codePostal : code postal du client
   * @return la liste des clients avec les critères
   */
  List<ClientDto> rechercherClients(String ville, String codePostal, String nomClient,
      String prenomClient);

  List<String> rechercherVilles(String ville);

  List<String> rechercheCodePostaux(String codePostal);

  List<String> rechercherPrenoms(String prenom);

  List<String> rechercherNoms(String nom);

  /**
   * Selectionne le client qui a comme id le paramètre id.
   * 
   * @param id : id du client à rechercher
   * @return le clients à rechercher
   */
  ClientDto getClientById(int id);

  /**
   * Liste tous les clients qui ne sont pas liés à un utilisateur.
   * 
   * @return la liste des clients non liés
   */
  List<ClientDto> rechercherClientsPasUtilisateur();

  /**
   * Selectionne le client qui a comme id le paramètre idClient.
   * 
   * @param idClient : id du client à rechercher
   * @return le clients à rechercher
   */
  ClientDto rechercherClientAvecId(int idClient);
}

package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.ClientDto;

import java.util.List;

public interface ClientDao {

  ClientDto insererClient(ClientDto client);

  List<ClientDto> listerClients();

  List<ClientDto> rechercherClients(String ville, String codePostal, String nomClient,
      String prenomClient);

  List<String> rechercherVilles(String ville);

  List<String> rechercheCodePostaux(String codePostal);

  List<String> rechercherPrenoms(String prenom);

  List<String> rechercherNoms(String nom);

  ClientDto getClientById(int id);
}

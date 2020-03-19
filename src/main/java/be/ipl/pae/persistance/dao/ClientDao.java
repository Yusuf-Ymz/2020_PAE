package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.ClientDto;

import java.util.List;

public interface ClientDao {

  ClientDto insererClient(ClientDto client);

  List<ClientDto> listerClients();

  List<ClientDto> RechercherClients(String ville, String codePostal, String nomClient,
      String prenomClient);

  List<String> RechercherNomsPrenomsClients(String nomClient, String prenomClient);

  List<String> RechercherVilles(String villes);

  public List<String> RechercheCodePostaux(String codePostal);



}

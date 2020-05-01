package mock.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.persistance.dao.ClientDao;

import java.util.ArrayList;
import java.util.List;

public class MockClientDao implements ClientDao {

  @Inject
  private DtoFactory dtoFactory;

  @Override
  public ClientDto insererClient(ClientDto client) {

    if (client != null) {
      ClientDto clientDto = this.dtoFactory.getClientDto();
      clientDto.setIdClient(1);
      return clientDto;
    }
    return null;
  }

  @Override
  public List<ClientDto> listerClients() {

    List<ClientDto> listeClients = new ArrayList<ClientDto>();

    for (int i = 0; i < 3; i++) {

      ClientDto client = this.dtoFactory.getClientDto();

      client.setNom("nom" + i);
      client.setPrenom("prenom" + i);

      listeClients.add(client);


    }

    return listeClients;
  }

  @Override
  public List<ClientDto> rechercherClients(String ville, String codePostal, String nomClient,
      String prenomClient) {

    List<ClientDto> listeClient = new ArrayList<ClientDto>();
    ClientDto mockClient = this.dtoFactory.getClientDto();

    if (ville.equals("Bruxelles") && codePostal.equals("1070") && prenomClient.equals("Alex")
        && nomClient.equals("Terieur")) {
      listeClient.add(mockClient);
    }
    if (ville.equals("Liege") && codePostal.equals("4031") && prenomClient.equals("Alain")
        && nomClient.equals("Terieur")) {
      listeClient.add(mockClient);
    }
    if (ville.equals("Bruxelles") && codePostal.equals("1020") && prenomClient.equals("Sara")
        && nomClient.equals("Croche")) {
      listeClient.add(mockClient);
    }
    if (ville.equals("Namur") && codePostal.equals("5000") && prenomClient.equals("Gerard")
        && nomClient.equals("Menvussa")) {
      listeClient.add(mockClient);
    }

    if (ville.equals("Bruxelles") && codePostal.equals("1000") && prenomClient.equals("Rihanna")
        && nomClient.equals("Voir")) {
      listeClient.add(mockClient);
    }

    return listeClient;
  }

  @Override
  public List<String> rechercherVilles(String ville) {
    List<String> listeVilleComme = new ArrayList<String>();

    String mockVille1 = "bruxelles";
    String mockVille2 = "brugges";

    listeVilleComme.add(mockVille1);
    listeVilleComme.add(mockVille2);

    return listeVilleComme;
  }

  @Override
  public List<String> rechercheCodePostaux(String codePostal) {
    List<String> listeCp = new ArrayList<String>();
    String mockCp1 = "1030";
    String mockCp2 = "1080";
    listeCp.add(mockCp1);
    listeCp.add(mockCp2);
    return listeCp;
  }

  @Override
  public List<String> rechercherPrenoms(String prenom) {
    List<String> listePrenomComme = new ArrayList<String>();

    String nom1 = "Soumaya";
    String nom2 = "Soulaimane";
    listePrenomComme.add(nom1);
    listePrenomComme.add(nom2);

    return listePrenomComme;
  }

  @Override
  public List<String> rechercherNoms(String nom) {
    List<String> listeNomComme = new ArrayList<String>();

    String nom1 = "scott";
    String nom2 = "scool";
    listeNomComme.add(nom1);
    listeNomComme.add(nom2);
    return listeNomComme;
  }

  @Override
  public ClientDto getClientById(int id) {

    List<ClientDto> listeClients = new ArrayList<ClientDto>();
    for (int i = 0; i < 5; i++) {
      ClientDto client = this.dtoFactory.getClientDto();

      client.setNom("nom" + i);
      client.setPrenom("prenom" + i);
      client.setIdClient(i);

      listeClients.add(client);

    }
    return listeClients.stream().filter(c -> c.getIdClient() == id).findAny().orElse(null);

  }

  @Override
  public List<ClientDto> rechercherClientsPasUtilisateur() {

    List<ClientDto> listeClients = new ArrayList<ClientDto>();
    for (int i = 0; i < 5; i++) {
      ClientDto client = this.dtoFactory.getClientDto();

      client.setNom("nom" + i);
      client.setPrenom("prenom" + i);
      client.setIdClient(i);

      listeClients.add(client);

    }

    return listeClients;
  }

  @Override
  public ClientDto rechercherClientAvecId(int idClient) {
    ClientDto client = this.dtoFactory.getClientDto();
    client.setIdClient(idClient);
    return client;
  }

  @Override
  public List<ClientDto> rechercherClientsNonLie(String ville, String codePostal, String nomClient,
      String prenomClient) {

    return this.rechercherClients(ville, codePostal, nomClient, prenomClient);
  }

  @Override
  public List<String> rechercherVillesClientNonLie(String ville) {

    return this.rechercherVilles(ville);
  }

  @Override
  public List<String> rechercheCodePostauxClientNonLie(String codePostal) {

    return this.rechercheCodePostaux(codePostal);
  }

  @Override
  public List<String> rechercherPrenomsClientNonLie(String prenom) {

    return this.rechercherPrenoms(prenom);
  }

  @Override
  public List<String> rechercherNomsClientNonLie(String nom) {

    return this.rechercherNoms(nom);
  }

}

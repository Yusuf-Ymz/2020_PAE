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
    // TODO Auto-generated method stub
    ClientDto mockClient = this.dtoFactory.getClientDto();

    mockClient.setVille(ville);
    mockClient.setCodePostal(codePostal);
    mockClient.setNom(nomClient);
    mockClient.setPrenom(prenomClient);

    List<ClientDto> listeClient = new ArrayList<ClientDto>();
    listeClient.add(mockClient);

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
    // TODO Auto-generated method stub

    return null;
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

    ClientDto client = this.dtoFactory.getClientDto();

    client.setNom("nom1");
    client.setPrenom("prenom1");
    client.setIdClient(id);


    return client;

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
    return client;
  }

}

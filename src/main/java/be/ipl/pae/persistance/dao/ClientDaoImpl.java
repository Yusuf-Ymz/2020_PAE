package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalBackendServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ClientDaoImpl extends DaoUtils implements ClientDao {
  @Inject
  DalBackendServices dal;
  @Inject
  DtoFactory fact;

  @Override
  public ClientDto insererClient(ClientDto client) {
    String query =
        "INSERT INTO pae.clients VALUES( DEFAULT,?,?,?,?,?,?,?, ?,?) RETURNING client_id;";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setString(1, client.getNom());
      prepareStatement.setString(2, client.getPrenom());
      prepareStatement.setString(3, client.getCodePostal());
      prepareStatement.setString(4, client.getVille());
      prepareStatement.setString(5, client.getEmail());
      prepareStatement.setString(6, client.getTelephone());
      prepareStatement.setString(7, client.getRue());


      ResultSet rs = prepareStatement.executeQuery();
      if (rs.next()) {
        return getClientById(rs.getInt(1));
      }
      return null;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }


  }

  @Override
  public List<ClientDto> listerClients() {
    String query = "SELECT * FROM pae.clients c ORDER BY c.nom";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      ResultSet rs = prepareStatement.executeQuery();

      List<ClientDto> listeClients = new ArrayList<ClientDto>();
      while (rs.next()) {
        ClientDto clientDto = fact.getClientDto();
        fillObject(clientDto, rs);
        listeClients.add(clientDto);
      }
      return listeClients;

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<ClientDto> rechercherClients(String ville, String codePostal, String nomClient,
      String prenomClient) {
    ville = ville.replace("%", "\\" + "%");
    ville += "%";
    codePostal = codePostal.replace("%", "\\" + "%");
    codePostal += "%";
    prenomClient = prenomClient.replace("%", "\\" + "%");
    prenomClient += "%";
    nomClient = nomClient.replace("%", "\\" + "%");
    nomClient += "%";
    String query =
        "SELECT * FROM pae.clients cl WHERE LOWER(cl.nom) LIKE LOWER(?) AND LOWER(cl.prenom) LIKE LOWER(?) AND lower(cl.ville) LIKE LOWER(?) AND cl.code_postal LIKE ? ORDER BY cl.nom ;";
    PreparedStatement prepareStatement = dal.createStatement(query);
    List<ClientDto> clients = new ArrayList<ClientDto>();
    try {
      prepareStatement.setString(1, nomClient);
      prepareStatement.setString(2, prenomClient);
      prepareStatement.setString(3, ville);
      prepareStatement.setString(4, codePostal);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        ClientDto client = fact.getClientDto();
        fillObject(client, rs);
        clients.add(client);
      }
      return clients;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<String> rechercherVilles(String ville) {
    ville = ville.replace("%", "\\" + "%");
    ville += "%";
    String query =
        "SELECT DISTINCT c.ville FROM pae.clients c WHERE LOWER(c.ville) LIKE LOWER(?) ORDER BY 1 ASC LIMIT 5 ;";
    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> villes = new ArrayList<String>();
    try {
      prepareStatement.setString(1, ville);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String villeDb = rs.getString(1);
        villes.add(villeDb);
      }
      return villes;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }

  }

  @Override
  public List<String> rechercheCodePostaux(String codePostal) {
    codePostal = codePostal.replace("%", "\\" + "%");
    codePostal += "%";
    String query =
        "SELECT DISTINCT c.code_postal FROM pae.clients c WHERE c.code_postal LIKE ? ORDER BY 1 ASC LIMIT 5";
    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> codePostaux = new ArrayList<String>();
    try {
      prepareStatement.setString(1, codePostal);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String codePostalDb = rs.getString(1);
        codePostaux.add(codePostalDb);
      }
      return codePostaux;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<String> rechercherPrenoms(String prenom) {
    prenom = prenom.replace("%", "\\" + "%");
    prenom += "%";
    String query =
        "SELECT DISTINCT c.prenom FROM pae.clients c WHERE LOWER(c.prenom) LIKE LOWER(?) ORDER BY 1 ASC LIMIT 5";
    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> prenoms = new ArrayList<String>();
    try {
      prepareStatement.setString(1, prenom);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String prenomDb = rs.getString(1);
        prenoms.add(prenomDb);
      }
      return prenoms;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<String> rechercherNoms(String nom) {
    nom = nom.replace("%", "\\" + "%");
    nom += "%";
    String query =
        "SELECT DISTINCT c.nom FROM pae.clients c WHERE LOWER(c.nom) LIKE LOWER(?) ORDER BY 1 ASC LIMIT 5";
    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> noms = new ArrayList<String>();
    try {
      prepareStatement.setString(1, nom);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String nomDb = rs.getString(1);
        noms.add(nomDb);
      }
      return noms;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public ClientDto getClientById(int id) {
    String query = "SELECT * FROM pae.clients c WHERE c.client_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, id);
      ResultSet rs = prepareStatement.executeQuery();
      ClientDto client = fact.getClientDto();
      if (rs.next()) {
        fillObject(client, rs);
      }
      return client;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<ClientDto> rechercherClientsPasUtilisateur() {
    String query =
        "SELECT * from pae.clients cl where cl.client_id NOT IN (SELECT u.client_id from pae.utilisateurs u WHERE u.client_id IS NOT NULL)";
    PreparedStatement prepareStatement = dal.createStatement(query);
    List<ClientDto> clients = new ArrayList<ClientDto>();
    try {
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        ClientDto client = fact.getClientDto();
        fillObject(client, rs);
        clients.add(client);
      }
      System.out.println(clients);
      return clients;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public UserDto rechercherClientAvecId(int idClient) {
    String query = "SELECT * FROM pae.clients WHERE client_id = ? ";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, idClient);
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        return null;
      }
      UserDto client = fact.getUserDto();
      return client;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }



}

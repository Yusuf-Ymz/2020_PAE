package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ClientDaoImpl implements ClientDao {
  @Inject
  DalService dal;
  @Inject
  DtoFactory fact;

  @Override
  public ClientDto insererClient(ClientDto client) {
    String query = "";
    PreparedStatement pStatement = dal.createStatement(query);
    try {
      ResultSet rs = pStatement.executeQuery();
      if (rs.next()) {

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
        dal.fillObject(clientDto, dal.convertResulSetToMap(rs));
        listeClients.add(clientDto);
      }
      return listeClients;

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<ClientDto> RechercherClients(String ville, String codePostal, String nomClient,
      String prenomClient) {
    String query =
        "SELECT * FROM pae.clients c WHERE LOWER(c.nom) LIKE LOWER(?) AND LOWER(c.prenom) LIKE LOWER(?) AND LOWER(c.ville) LIKE LOWER(?) AND c.code_postal = ? ";
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
        dal.fillObject(client, dal.convertResulSetToMap(rs));
        clients.add(client);
      }
      return clients;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<String> RechercherNomsPrenomsClients(String nomClient, String prenomClient) {
    nomClient = nomClient.replace("%", "\\" + "%");
    nomClient += "%";
    prenomClient = prenomClient.replace("%", "\\" + "%");
    prenomClient += "%";
    String query =
        "SELECT c.nom, c.prenom FROM pae.clients WHERE c.prenom LIKE ? OR c.nom LIKE ?; ";
    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> codePostaux = new ArrayList<String>();
    try {
      prepareStatement.setString(1, prenomClient);
      prepareStatement.setString(2, nomClient);

      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String nom = rs.getString(1);
        String prenom = rs.getString(2);
        codePostaux.add(nom + " " + prenom);
      }
      return codePostaux;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<String> RechercherVilles(String ville) {
    ville = ville.replace("%", "\\" + "%");
    ville += "%";
    String query = "SELECT c.ville FROM pae.clients WHERE c.ville LIKE ?; ";
    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> villes = new ArrayList<String>();
    try {
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String villeDB = rs.getString(1);
        villes.add(villeDB);
      }
      return villes;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }

  }

  @Override
  public List<String> RechercheCodePostaux(String codePostal) {
    codePostal = codePostal.replace("%", "\\" + "%");
    codePostal += "%";
    String query = "SELECT c.code_postal FROM pae.clients WHERE c.code_postal LIKE ?; ";
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


}

package be.ipl.pae.persistance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalBackendServices;

public class DevisDaoImpl extends DaoUtils implements DevisDao {
  @Inject
  private DalBackendServices dal;
  @Inject
  private DtoFactory fact;

  @Override
  public UserDto obtenirUserAvecId(int idUser) {
    String query = "Select * FROM pae.utilisateurs WHERE utilisateur_id = ? ";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, idUser);
      ResultSet rs = prepareStatement.executeQuery();
      if (!rs.next()) {
        return null;
      }
      UserDto user = fact.getUserDto();
      fillObject(user, rs);
      return user;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public List<DevisDto> obtenirTousLesDevis() {
    String query =
        "SELECT * FROM pae.clients c, pae.devis d LEFT OUTER JOIN pae.photos p ON d.photo_preferee=p.photo_id ORDER BY date_debut";
    PreparedStatement prepareStatement = dal.createStatement(query);

    String queryAmenagements =
        "SELECT * FROM pae.devis d, pae.travaux t, pae.types_amenagements ta WHERE d.devis_id=t.devis_id AND ta.type_amenagement=t.type_amenagement AND d.devis_id = ?";
    PreparedStatement prepareQueryAmenagements = dal.createStatement(queryAmenagements);

    try {
      ResultSet rs = prepareStatement.executeQuery();

      List<DevisDto> listeDevis = new ArrayList<DevisDto>();
      while (rs.next()) {
        DevisDto devis = fact.getDevisDto();
        fillObject(devis, rs);

        ClientDto client = fact.getClientDto();
        fillObject(client, rs);

        devis.setClient(client);

        prepareQueryAmenagements.setInt(1, devis.getDevisId());
        rs = prepareQueryAmenagements.executeQuery();
        List<AmenagementDto> listeAmenagements = new ArrayList<AmenagementDto>();

        while (rs.next()) {
          AmenagementDto amenagement = fact.getAmenagementDto();
          fillObject(amenagement, rs);
          listeAmenagements.add(amenagement);
        }

        devis.setAmenagements(listeAmenagements);

        listeDevis.add(devis);
      }
      return listeDevis;

    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  public List<DevisDto> obtenirSesDevis(int idClient) {
    String query =
        "SELECT * FROM pae.clients c, pae.devis d LEFT OUTER JOIN pae.photos p ON d.photo_preferee=p.photo_id WHERE d.client = ? ORDER BY date_debut";
    PreparedStatement prepareStatement = dal.createStatement(query);

    String queryAmenagements =
        "SELECT * FROM pae.devis d, pae.travaux t, pae.types_amenagements ta WHERE d.devis_id=t.devis_id AND ta.type_amenagement=t.type_amenagement AND d.devis_id = ?";
    PreparedStatement prepareQueryAmenagements = dal.createStatement(queryAmenagements);

    /*
     * String queryPhotos =
     * "SELECT count(p.*) AS 'toutes les photos',COALESCE(SUM(p.avant_apres),0) FROM pae.devis d, pae.photos p WHERE p.devis=d.devis_id AND d.client = ?"
     * ; PreparedStatement prepareQueryPhotos = dal.createStatement(queryPhotos);
     */
    try {
      prepareStatement.setInt(1, idClient);
      ResultSet rs = prepareStatement.executeQuery();

      List<DevisDto> listeDevis = new ArrayList<DevisDto>();
      while (rs.next()) {
        DevisDto devis = fact.getDevisDto();
        fillObject(devis, rs);

        ClientDto client = fact.getClientDto();
        fillObject(client, rs);

        devis.setClient(client);

        prepareQueryAmenagements.setInt(1, devis.getDevisId());
        rs = prepareQueryAmenagements.executeQuery();
        List<AmenagementDto> listeAmenagements = new ArrayList<AmenagementDto>();

        while (rs.next()) {
          AmenagementDto amenagement = fact.getAmenagementDto();
          fillObject(amenagement, rs);
          listeAmenagements.add(amenagement);
        }

        devis.setAmenagements(listeAmenagements);
        listeDevis.add(devis);
      }

      return listeDevis;

    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  @Override
  public DevisDto insererDevis(int idOuvrier, DevisDto devis) {
    String query = "";
    PreparedStatement prepareStatement = dal.createStatement(query);

    return null;
  }

  // Faire passer l'état du devis à accepté (Simple update).
  public void confirerDateDevis(int idDevis) {

  }

}

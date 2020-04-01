package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalBackendServices;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevisDaoImpl extends DaoUtils implements DevisDao {
  @Inject
  private DalBackendServices dal;
  @Inject
  private DtoFactory fact;

  @Override
  public List<DevisDto> obtenirTousLesDevis() {
    String query = "SELECT * FROM pae.clients c, pae.devis d "
        + "LEFT OUTER JOIN pae.photos p ON d.photo_preferee=p.photo_id "
        + "WHERE c.client_id=d.client ORDER BY date_debut";
    PreparedStatement prepareStatement = dal.createStatement(query);

    try {
      ResultSet rs = prepareStatement.executeQuery();

      List<DevisDto> listeDevis = new ArrayList<DevisDto>();
      while (rs.next()) {
        DevisDto devis = fact.getDevisDto();
        fillObject(devis, rs);

        ClientDto client = fact.getClientDto();
        fillObject(client, rs);
        devis.setClient(client);

        PhotoDto photoPreferee = fact.getPhotoDto();
        fillObject(photoPreferee, rs);
        devis.setPhotoPreferee(photoPreferee);

        recupererLesAmenagementsDunDevis(devis);
        listeDevis.add(devis);
      }

      return listeDevis;

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public List<DevisDto> obtenirSesDevis(int idClient) {
    String query = "SELECT * FROM pae.devis d "
        + "LEFT OUTER JOIN pae.photos p ON d.photo_preferee=p.photo_id "
        + "WHERE d.client = ? ORDER BY date_debut";
    PreparedStatement prepareStatement = dal.createStatement(query);

    try {
      prepareStatement.setInt(1, idClient);
      ResultSet rs = prepareStatement.executeQuery();

      List<DevisDto> listeDevis = new ArrayList<DevisDto>();
      while (rs.next()) {
        DevisDto devis = fact.getDevisDto();
        fillObject(devis, rs);

        PhotoDto photoPreferee = fact.getPhotoDto();
        fillObject(photoPreferee, rs);
        photoPreferee.setDevis(devis);

        devis.setPhotoPreferee(photoPreferee);

        recupererLesAmenagementsDunDevis(devis);
        listeDevis.add(devis);
      }

      return listeDevis;

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  private void recupererLesAmenagementsDunDevis(DevisDto devis) throws SQLException {
    String queryAmenagements =
        "SELECT * FROM pae.devis d, pae.travaux t, pae.types_amenagements ta "
            + "WHERE d.devis_id=t.devis_id AND "
            + "ta.type_amenagement=t.type_amenagement AND d.devis_id = ?";
    PreparedStatement prepareStatement = this.dal.createStatement(queryAmenagements);

    prepareStatement.setInt(1, devis.getDevisId());
    ResultSet rs = prepareStatement.executeQuery();

    List<AmenagementDto> listeAmenagements = new ArrayList<AmenagementDto>();
    while (rs.next()) {
      AmenagementDto amenagement = fact.getAmenagementDto();
      fillObject(amenagement, rs);
      listeAmenagements.add(amenagement);
    }

    devis.setAmenagements(listeAmenagements);


  }

  @Override
  public DevisDto consulterDevisEnTantQuOuvrier(int devisId) {
    try {
      String queryDevis =
          "SELECT * FROM pae.clients c, pae.devis d WHERE d.devis_id = ? AND d.client=c.client_id";

      PreparedStatement prepareStatement = dal.createStatement(queryDevis);
      prepareStatement.setInt(1, devisId);

      DevisDto devis = recupererDevisAvecSonClient(prepareStatement);

      if (devis == null) {
        return null;
      }

      recupererLesAmenagementsDunDevis(devis);
      recupererLesPhotosAvantDunDevis(devis);
      recupererLesPhotosApresDunDevis(devis);

      return devis;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public DevisDto consulterDevisEnTantQueUtilisateur(int clientId, int devisId) {
    // TODO Auto-generated method stub

    try {
      String queryDevis = "SELECT * FROM pae.clients c, pae.devis d "
          + "WHERE d.devis_id = ? AND c.client_id = ? AND d.client=c.client_id";

      PreparedStatement prepareStatement = dal.createStatement(queryDevis);
      prepareStatement.setInt(1, devisId);
      prepareStatement.setInt(2, clientId);

      DevisDto devis = recupererDevisAvecSonClient(prepareStatement);
      if (devis == null) {
        return null;
      }

      recupererLesAmenagementsDunDevis(devis);
      recupererLesPhotosAvantDunDevis(devis);
      recupererLesPhotosApresDunDevis(devis);
      return devis;

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  private DevisDto recupererDevisAvecSonClient(PreparedStatement prepareStatement) {
    ResultSet rs;
    try {
      rs = prepareStatement.executeQuery();

      if (!rs.next()) {
        return null;
      }

      DevisDto devis = fact.getDevisDto();
      fillObject(devis, rs);

      ClientDto client = fact.getClientDto();
      fillObject(client, rs);

      devis.setClient(client);

      return devis;
    } catch (SQLException exception) {
      // TODO Auto-generated catch block
      exception.printStackTrace();
      throw new FatalException();
    }

  }

  private void recupererLesPhotosAvantDunDevis(DevisDto devis) throws SQLException {
    String queryPhotoAvant = "SELECT * FROM pae.devis d,pae.photos p "
        + "WHERE p.devis=d.devis_id AND p.avant_apres=false AND d.devis_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(queryPhotoAvant);

    prepareStatement.setInt(1, devis.getDevisId());
    ResultSet rs = prepareStatement.executeQuery();

    List<PhotoDto> photosAvant = new ArrayList<PhotoDto>();
    while (rs.next()) {
      PhotoDto photo = fact.getPhotoDto();
      fillObject(photo, rs);
      photosAvant.add(photo);
    }

    devis.setPhotosAvant(photosAvant);

  }

  private void recupererLesPhotosApresDunDevis(DevisDto devis) throws SQLException {
    String queryPhotoApres = "SELECT * FROM pae.devis d,pae.photos p "
        + "WHERE p.devis=d.devis_id AND p.avant_apres=true AND d.devis_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(queryPhotoApres);

    prepareStatement.setInt(1, devis.getDevisId());
    ResultSet rs = prepareStatement.executeQuery();

    List<PhotoDto> photosApres = new ArrayList<PhotoDto>();
    while (rs.next()) {
      PhotoDto photo = fact.getPhotoDto();
      fillObject(photo, rs);
      photosApres.add(photo);
    }

    devis.setPhotoApres(photosApres);

  }

  // Possibilité de faire une seule méthode pour tous les états.
  @Override
  public void accepterDateTravaux(int numeroDevis) {
    String query = "UPDATE pae.devis SET etat = 'accepté' WHERE devis_id = ?";
    PreparedStatement preparedStatement = dal.createStatement(query);
    try {
      preparedStatement.setInt(1, numeroDevis);

      preparedStatement.execute();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public DevisDto insererDevis(DevisDto devis, String[] photos) {
    String query = "INSERT INTO pae.devis "
        + "VALUES (DEFAULT,?,?,?,?,'Devis introduit',CURRENT_TIMESTAMP,NULL) "
        + "RETURNING devis_id;";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, devis.getClient().getIdClient());
      prepareStatement.setDate(2, Date.valueOf(devis.getDateDebut()));
      prepareStatement.setInt(3, devis.getMontantTotal());
      prepareStatement.setInt(4, devis.getDuree());

      ResultSet rs = prepareStatement.executeQuery();
      int idDevis = -1;
      if (rs.next()) {
        idDevis = rs.getInt(1);
        devis.setDevisId(idDevis);
      }
      insererTravaux(devis);
      insererPhotos(devis, photos);
      DevisDto newDevis = obtenirDevisById(idDevis);
      return newDevis;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  private void insererPhotos(DevisDto devis, String[] photos) {
    String query = "INSERT INTO pae.photos VALUES (DEFAULT,?,FALSE,FALSE,NULL,?)";
    PreparedStatement ps = dal.createStatement(query);
    try {
      ps.setInt(2, devis.getDevisId());
      for (String photo : photos) {
        ps.setString(1, photo);
        ps.execute();
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();

    }
  }

  private void insererTravaux(DevisDto devis) {
    String query = "INSERT INTO pae.travaux VALUES (?,?)";

    List<AmenagementDto> amenagements = devis.getAmenagements();
    System.out.println(devis.getDevisId());
    try {
      for (AmenagementDto amenagement : amenagements) {
        PreparedStatement ps = dal.createStatement(query);
        System.out.println(amenagement.getId());
        ps.setInt(1, devis.getDevisId());
        ps.setInt(2, amenagement.getId());
        ps.execute();
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }


  private DevisDto obtenirDevisById(int id) {
    String query = "SELECT * FROM pae.clients c, pae.devis d "
        + "LEFT OUTER JOIN pae.photos p ON d.photo_preferee=p.photo_id "
        + "WHERE d.client = c.client_id AND d.devis_id = ?";
    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, id);
      ResultSet rs = prepareStatement.executeQuery();
      DevisDto devis = fact.getDevisDto();
      ClientDto client = fact.getClientDto();
      if (rs.next()) {
        fillObject(client, rs);
        fillObject(devis, rs);
        devis.setClient(client);
      }
      return devis;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public void changerEtatDevis(int idDevis, String newEtat) {
    String query = "UPDATE pae.devis SET etat = ? WHERE devis_id = ?";
    PreparedStatement preparedStatement = dal.createStatement(query);
    try {
      preparedStatement.setString(1, newEtat);
      preparedStatement.setInt(2, idDevis);
      System.out.println(preparedStatement);
      preparedStatement.execute();
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException("Impossible de changer l'état du devis.");
    }
  }

  @Override
  public String getEtatActuel(int idDevis) {
    String query = "SELECT etat FROM pae.devis WHERE devis_id = ?;";
    PreparedStatement preparedStatement = dal.createStatement(query);
    String etatActuel = null;
    try {
      preparedStatement.setInt(1, idDevis);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        etatActuel = rs.getString(1);
        return etatActuel;
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return etatActuel;
  }

}

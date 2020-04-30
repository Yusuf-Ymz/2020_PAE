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
import java.time.LocalDate;
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
    try {
      for (AmenagementDto amenagement : amenagements) {
        PreparedStatement ps = dal.createStatement(query);
        ps.setInt(1, devis.getDevisId());
        ps.setInt(2, amenagement.getId());
        ps.execute();
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public DevisDto obtenirDevisById(int id) {
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
        PhotoDto photoPreferee = fact.getPhotoDto();
        fillObject(photoPreferee, rs);
        devis.setPhotoPreferee(photoPreferee);
      }
      recupererLesAmenagementsDunDevis(devis);

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

      return null;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public PhotoDto insererPhotoApresAmenagement(PhotoDto photo) {
    String query = "INSERT INTO pae.photos VALUES (DEFAULT,?,TRUE,?,?,?) RETURNING photo_id";
    PreparedStatement ps = dal.createStatement(query);
    try {
      ps.setString(1, photo.getPhoto());
      ps.setBoolean(2, photo.isVisible());
      ps.setInt(3, photo.getAmenagement().getId());
      ps.setInt(4, photo.getDevis().getDevisId());
      ResultSet rs = ps.executeQuery();
      PhotoDto newPhoto = null;
      if (rs.next()) {
        int idPhoto = rs.getInt(1);
        if (photo.isPreferee()) {
          insererPhotoPreferee(photo.getDevis().getDevisId(), idPhoto);
        }
        newPhoto = getPhotoById(idPhoto);
      }
      return newPhoto;

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }



  private PhotoDto getPhotoById(int idPhoto) {
    String query = "SELECT * FROM pae.photos WHERE photo_id = ?";
    PreparedStatement ps = dal.createStatement(query);
    PhotoDto photo = fact.getPhotoDto();
    try {
      ps.setInt(1, idPhoto);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        fillObject(photo, rs);
        return photo;
      }
      return null;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }

  private void insererPhotoPreferee(int devisId, int idPhoto) {
    String query = "UPDATE pae.devis SET photo_preferee = ? WHERE devis_id = ?";
    PreparedStatement ps = dal.createStatement(query);
    try {
      ps.setInt(1, idPhoto);
      ps.setInt(2, devisId);
      ps.execute();
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }

  @Override
  public List<String> rechercherNomsClients(String nom) {
    // TODO Auto-generated method stub
    nom = nom.replace("%", "\\" + "%");
    nom += "%";
    String query = "SELECT DISTINCT c.nom FROM pae.clients c, pae.devis d "
        + "WHERE d.client=c.client_id AND " + "LOWER(c.nom) LIKE LOWER(?) ORDER BY 1 ASC LIMIT 5 ";

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
  public List<String> rechercherPrenomsClients(String prenom) {
    // TODO Auto-generated method stub
    prenom = prenom.replace("%", "\\" + "%");
    prenom += "%";
    String query = "SELECT DISTINCT c.prenom FROM pae.clients c, pae.devis d "
        + "WHERE d.client=c.client_id AND "
        + "LOWER(c.prenom) LIKE LOWER(?) ORDER BY 1 ASC LIMIT 5 ";

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
  public List<String> rechercherAmenagementsDesDevisDeTousLesClients(String amenagement) {
    amenagement = amenagement.replace("%", "\\" + "%");
    amenagement += "%";
    String query =
        "SELECT DISTINCT ta.libelle FROM pae.travaux tr, pae.devis d, pae.types_amenagements ta "
            + "WHERE d.devis_id=tr.devis_id AND tr.type_amenagement=ta.type_amenagement "
            + "AND LOWER(ta.libelle) LIKE LOWER(?) ORDER BY 1 ASC LIMIT 5 ";

    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> amenagements = new ArrayList<String>();
    try {
      prepareStatement.setString(1, amenagement);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String amenagementDb = rs.getString(1);
        amenagements.add(amenagementDb);
      }
      return amenagements;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<DevisDto> rechercherTousLesDevisAffine(String nomClient, String prenomClient,
      String typeAmenagement, String dateDevis, int montantMin, int montantMax) {
    String query = null;

    query =
        "SELECT * FROM pae.devis d LEFT OUTER JOIN pae.photos p ON p.photo_id=d.photo_preferee, "
            + "pae.clients c ";

    if (!typeAmenagement.isEmpty()) {
      query += ",pae.travaux tr, pae.types_amenagements ta ";
    }

    query += "WHERE c.client_id=d.client AND " + "LOWER(c.nom) LIKE LOWER(?) AND "
        + "LOWER(c.prenom) LIKE LOWER(?) AND ";

    if (!typeAmenagement.isEmpty()) {
      query += "tr.devis_id=d.devis_id AND " + "tr.type_amenagement=ta.type_amenagement AND "
          + "LOWER(ta.libelle) LIKE LOWER(?) AND ";
    }

    if (!dateDevis.isEmpty()) {
      query += "d.date_devis=? AND ";
    }

    query += "d.montant_total BETWEEN ? AND ? ";
    query += "ORDER BY d.date_debut";

    PreparedStatement prepareStatement = dal.createStatement(query);

    nomClient = nomClient.replace("%", "\\" + "%");
    nomClient += "%";

    prenomClient = prenomClient.replace("%", "\\" + "%");
    prenomClient += "%";

    try {
      prepareStatement.setString(1, nomClient);
      prepareStatement.setString(2, prenomClient);
      int indiceDansLaRequete = 3;

      return rechercherDevisAffine(prepareStatement, false, typeAmenagement, dateDevis, montantMin,
          montantMax, indiceDansLaRequete);

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<String> rechercherAmenagementsDesDevisDUnClient(String amenagement, int clientId) {
    amenagement = amenagement.replace("%", "\\" + "%");
    amenagement += "%";
    String query = "SELECT DISTINCT ta.libelle FROM pae.clients c,"
        + "pae.travaux tr, pae.devis d, pae.types_amenagements ta "
        + "WHERE c.client_id=d.client AND d.devis_id=tr.devis_id AND "
        + "tr.type_amenagement=ta.type_amenagement AND "
        + "c.client_id = ? AND LOWER(ta.libelle) LIKE LOWER(?) ORDER BY 1 ASC LIMIT 5 ";

    PreparedStatement prepareStatement = dal.createStatement(query);
    List<String> amenagements = new ArrayList<String>();
    try {
      prepareStatement.setInt(1, clientId);
      prepareStatement.setString(2, amenagement);
      ResultSet rs = prepareStatement.executeQuery();
      while (rs.next()) {
        String amenagementDb = rs.getString(1);
        amenagements.add(amenagementDb);
      }
      return amenagements;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public List<DevisDto> rechercherMesDevisAffine(int clientId, String typeAmenagement,
      String dateDevis, int montantMin, int montantMax) {
    String query = null;


    query =
        "SELECT * FROM pae.devis d LEFT OUTER JOIN pae.photos p ON p.photo_id=d.photo_preferee, "
            + "pae.clients c ";

    if (!typeAmenagement.isEmpty()) {
      query += ",pae.travaux tr, pae.types_amenagements ta ";
    }

    query += "WHERE c.client_id=d.client AND c.client_id = ? AND ";

    if (!typeAmenagement.isEmpty()) {
      query += "tr.devis_id=d.devis_id AND " + "tr.type_amenagement=ta.type_amenagement AND "
          + "LOWER(ta.libelle) LIKE LOWER(?) AND ";
    }

    if (!dateDevis.isEmpty()) {
      query += "d.date_devis=? AND ";
    }

    query += "d.montant_total BETWEEN ? AND ? ";
    query += "ORDER BY d.date_debut";


    PreparedStatement prepareStatement = dal.createStatement(query);
    try {
      prepareStatement.setInt(1, clientId);
      int indiceDansLaRequete = 2;

      return rechercherDevisAffine(prepareStatement, true, typeAmenagement, dateDevis, montantMin,
          montantMax, indiceDansLaRequete);

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  private List<DevisDto> rechercherDevisAffine(PreparedStatement prepareStatement, boolean mesDevis,
      String typeAmenagement, String dateDevis, int montantMin, int montantMax,
      int indiceDansLaRequete) throws SQLException {

    if (!typeAmenagement.isEmpty()) {
      typeAmenagement = typeAmenagement.replace("%", "\\" + "%");
      typeAmenagement += "%";
      prepareStatement.setString(indiceDansLaRequete, typeAmenagement);
      indiceDansLaRequete++;
    }

    if (!dateDevis.isEmpty()) {
      prepareStatement.setDate(indiceDansLaRequete, Date.valueOf(dateDevis));
      indiceDansLaRequete++;
    }

    prepareStatement.setInt(indiceDansLaRequete, montantMin);
    prepareStatement.setInt(indiceDansLaRequete + 1, montantMax);

    ResultSet rs = prepareStatement.executeQuery();

    List<DevisDto> listeDevis = new ArrayList<DevisDto>();
    while (rs.next()) {
      DevisDto devis = fact.getDevisDto();
      fillObject(devis, rs);

      if (!mesDevis) {
        ClientDto clientBis = fact.getClientDto();
        fillObject(clientBis, rs);
        devis.setClient(clientBis);
      }

      PhotoDto photoPreferee = fact.getPhotoDto();
      fillObject(photoPreferee, rs);
      devis.setPhotoPreferee(photoPreferee);

      recupererLesAmenagementsDunDevis(devis);
      listeDevis.add(devis);
    }

    return listeDevis;
  }

  @Override
  public void repousserDateTravaux(int idDevis, String newDate) {
    String query = "UPDATE pae.devis SET date_debut = ? WHERE devis_id = ?";
    PreparedStatement stmt = dal.createStatement(query);
    try {
      stmt.setInt(2, idDevis);
      stmt.setDate(1, Date.valueOf(LocalDate.parse(newDate)));
      stmt.execute();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  public String getDateDebut(int idDevis) {
    String query = "SELECT date_debut FROM pae.devis WHERE devis_id = ?;";

    PreparedStatement stmt = dal.createStatement(query);
    if (getEtatActuel(idDevis).equalsIgnoreCase("Annulé")) {
      throw new IllegalArgumentException();
    }
    try {
      stmt.setInt(1, idDevis);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return (String) rs.getObject(1).toString();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    throw new IllegalArgumentException();
  }

}

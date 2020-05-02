package mock.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.persistance.dao.DevisDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockDevisDao implements DevisDao {

  @Inject
  private DtoFactory dtoFactory;

  @Override
  public List<DevisDto> obtenirTousLesDevis() {
    // TODO Auto-generated method stub
    List<DevisDto> listeTousLesDevis = new ArrayList<DevisDto>();
    return listeTousLesDevis;
  }

  @Override
  public List<DevisDto> obtenirSesDevis(int idClient) {
    List<DevisDto> listeMesDevis = new ArrayList<DevisDto>();
    if (idClient == 1) {
      listeMesDevis.add(dtoFactory.getDevisDto());
    }
    // TODO Auto-generated method stub

    return listeMesDevis;
  }

  @Override
  public DevisDto consulterDevisEnTantQuOuvrier(int devisId) {
    // TODO Auto-generated method stub
    if (devisId == -1) {
      return null;
    }

    DevisDto devis = dtoFactory.getDevisDto();
    return devis;
  }

  @Override
  public DevisDto consulterDevisEnTantQueUtilisateur(int clientId, int devisId) {
    // TODO Auto-generated method stub
    if (clientId == 2 && devisId == 1) {
      return null;
    }

    DevisDto devis = dtoFactory.getDevisDto();
    return devis;
  }

  @Override
  public void accepterDateTravaux(int numeroDevis) {
    DevisDto devis = dtoFactory.getDevisDto();
    devis.setEtat("accepté");
    devis.setDevisId(numeroDevis);

  }

  @Override
  public DevisDto insererDevis(DevisDto devis, String[] photos) {
    DevisDto devis2 = dtoFactory.getDevisDto();
    List<PhotoDto> ph = new ArrayList<PhotoDto>();

    for (String photo : photos) {
      PhotoDto phtos = dtoFactory.getPhotoDto();
      phtos.setPhoto(photo);
      ph.add(phtos);
    }
    devis2.setPhotosAvant(ph);
    return devis2;
  }

  @Override
  public void changerEtatDevis(int idDevis, String newEtat) {}

  @Override
  public PhotoDto insererPhotoApresAmenagement(PhotoDto photo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DevisDto obtenirDevisById(int id) {
    // TODO Auto-generated method stub
    DevisDto devis = dtoFactory.getDevisDto();

    if (id == -1) {
      return null;
    }

    if (id == 1) {
      devis.setEtat("Devis introduit");
      devis.setDateDebut(LocalDate.parse("2019-01-01"));
    }

    if (id == 2) {
      devis.setEtat("Commande confirmée");
      devis.setDateDebut(LocalDate.parse("2020-02-01"));
      devis.setDateDevis(LocalDate.parse("2020-01-01"));
    }

    if (id == 3) {
      devis.setEtat("Acompte payé");
    }

    if (id == 4) {
      devis.setEtat("Facture de milieu chantier envoyée");
    }

    if (id == 5) {
      devis.setEtat("Facture de fin de chantier envoyée");
    }

    if (id == 6) {
      devis.setEtat("Visible");
    }

    if (id == 7) {
      devis.setEtat("Absence du paiement de l'acompte");
      devis.setDateDevis(LocalDate.parse("2019-01-01"));
    }

    if (id == 8) {
      devis.setEtat("Annulé");
    }

    return devis;
  }

  @Override
  public List<String> rechercherNomsClients(String nom) {
    // TODO Auto-generated method stub
    List<String> noms = new ArrayList<String>();

    if (nom == "test") {
      noms.add("hello");
    }

    return noms;
  }

  @Override
  public List<String> rechercherAmenagementsDesDevisDeTousLesClients(String amenagement) {
    // TODO Auto-generated method stub
    List<String> amenagements = new ArrayList<String>();

    if (amenagement == "test") {
      amenagements.add("hello");
    }

    return amenagements;
  }

  @Override
  public List<DevisDto> rechercherTousLesDevisAffine(String nomClient, String prenomClient,
      String typeAmenagement, String dateDevis, int montantMin, int montantMax) {
    List<DevisDto> devis = new ArrayList<DevisDto>();

    if (nomClient == "yusuf" && prenomClient == null && typeAmenagement == null && montantMin == -1
        && montantMax == -1 && dateDevis == null) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == null && prenomClient == "Elie" && typeAmenagement == null && montantMin == -1
        && montantMax == -1 && dateDevis == null) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == null && prenomClient == null && typeAmenagement == "test" && dateDevis == null
        && montantMin == -1 && montantMax == -1) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == null && prenomClient == null && typeAmenagement == null && dateDevis == null
        && montantMin == -1 && montantMax == 1000) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == null && prenomClient == null && typeAmenagement == null && dateDevis == null
        && montantMax == -1 && montantMin == 100) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == null && prenomClient == null && typeAmenagement == null && dateDevis != null
        && montantMax == -1 && montantMin == -1) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == "baptiste" && prenomClient == "Dupont" && typeAmenagement == null
        && dateDevis == null && montantMin == -1 && montantMax == -1) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == "baptiste" && prenomClient == "Dupont" && typeAmenagement == "basses-tiges"
        && dateDevis == null && montantMin == -1 && montantMax == -1) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == "paul" && prenomClient == "Dupont" && typeAmenagement == "basses-tiges"
        && dateDevis != null && montantMin == -1 && montantMax == -1) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == "charles" && prenomClient == "Dupont" && typeAmenagement == "basses-tiges"
        && dateDevis != null && montantMin == 100 && montantMax == -1) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (nomClient == "albert" && prenomClient == "Dupont" && typeAmenagement == "basses-tiges"
        && dateDevis != null && montantMin == 100 && montantMax == 1000) {
      devis.add(dtoFactory.getDevisDto());
    }



    return devis;
  }

  @Override
  public List<String> rechercherAmenagementsDesDevisDUnClient(String amenagement, int clientId) {
    List<String> amenagements = new ArrayList<String>();

    if (clientId == 1 && amenagement == "test") {
      amenagements.add("hello");
    }

    return amenagements;
  }

  @Override
  public List<DevisDto> rechercherMesDevisAffine(int clientId, String typeAmenagement,
      String dateDevis, int montantMin, int montantMax) {
    List<DevisDto> devis = new ArrayList<DevisDto>();

    if (clientId == 1 && typeAmenagement == "test" && dateDevis == null && montantMin == -1
        && montantMax == -1) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (clientId == 2 && montantMax == 1000) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (clientId == 3 && montantMin == 100) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (clientId == 4 && dateDevis != null) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (clientId == 5 && typeAmenagement == "test" && dateDevis != null && montantMin == -1
        && montantMax == -1) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (clientId == 6 && typeAmenagement == "test" && dateDevis != null && montantMin == 100
        && montantMax == -1) {
      devis.add(dtoFactory.getDevisDto());
    }

    if (clientId == 7 && typeAmenagement == "test" && dateDevis != null && montantMin == 100
        && montantMax == 1000) {
      devis.add(dtoFactory.getDevisDto());
    }

    return devis;
  }

  @Override
  public List<String> rechercherPrenomsClients(String prenom) {
    // TODO Auto-generated method stub
    List<String> prenoms = new ArrayList<>();

    if (prenom.equals("test")) {
      prenoms.add("hello");
    }

    return prenoms;
  }

  @Override
  public void repousserDateTravaux(int idDevis, LocalDate newDate) {
    // TODO Auto-generated method stub

  }

  @Override
  public void confirmerCommandeAmenagement(int idDevis, LocalDate date) {
    // TODO Auto-generated method stub

  }

  @Override
  public void supprimerDateDebutTravaux(int idDevis, String etat) {
    // TODO Auto-generated method stub

  }


}

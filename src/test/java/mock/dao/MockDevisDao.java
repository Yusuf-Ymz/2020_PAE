package mock.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.persistance.dao.DevisDao;

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
  public String getEtatActuel(int idDevis) {
    if (idDevis == 1) {
      return "Date début confirmée";
    }

    if (idDevis == 2) {
      return "Commande confirmée";
    }

    if (idDevis == 3) {
      return "Introduit";
    }

    return null;
  }



  @Override
  public PhotoDto insererPhotoApresAmenagement(PhotoDto photo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DevisDto obtenirDevisById(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> rechercherNomsClients(String nom) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> rechercherAmenagementsTousLesClients(String amenagement) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<DevisDto> rechercherTousLesDevisAffine(String client, String typeAmenagement,
      String dateDevis, int montantMin, int montantMax) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> rechercherAmenagements(String amenagement, int clientId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<DevisDto> rechercherMesDevisAffine(int clientId, String typeAmenagement,
      String dateDevis, int montantMin, int montantMax) {
    // TODO Auto-generated method stub
    return null;
  }

}

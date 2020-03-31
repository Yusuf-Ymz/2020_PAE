package mock.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
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
    if (idClient == 1) {
      return null;
    }
    // TODO Auto-generated method stub
    List<DevisDto> listeMesDevis = new ArrayList<DevisDto>();
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
    if (clientId == -1 || devisId == -1) {
      return null;
    }

    if (clientId == 2 && devisId == 1) {
      return null;
    }

    DevisDto devis = dtoFactory.getDevisDto();
    return devis;
  }

  @Override
  public void accepterDateTravaux(int numeroDevis) {
    // TODO Auto-generated method stub

  }

  @Override
  public DevisDto insererDevis(DevisDto devis, String[] photos) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void changerEtatDevis(int idDevis, String newEtat) {
    // TODO Auto-generated method stub

  }

  @Override
  public String getEtatActuel(int idDevis) {
    // TODO Auto-generated method stub
    return null;
  }

}

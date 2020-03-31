package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.AmenagementDao;
import be.ipl.pae.persistance.dao.ClientDao;
import be.ipl.pae.persistance.dao.DevisDao;
import be.ipl.pae.persistance.dao.UserDao;

import java.util.ArrayList;
import java.util.List;



class DevisUccImpl implements DevisUcc {

  @Inject
  private DalServices dal;
  @Inject
  private DevisDao devisdao;
  @Inject
  private UserDao userdao;
  @Inject
  private ClientDao clientDao;
  @Inject
  private AmenagementDao amenagementDao;

  public List<DevisDto> listerTousLesDevis() {
    try {
      dal.startTransaction();

      return this.devisdao.obtenirTousLesDevis();
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }


  public List<DevisDto> listerSesDevis(int idUser) {
    try {
      dal.startTransaction();

      UserDto user = this.userdao.obtenirUserAvecId(idUser);

      if (!user.isConfirme()) {
        throw new BizException("Cette action n'est pas réalisable");
      }

      return this.devisdao.obtenirSesDevis(user.getClientId());
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }


  }

  @Override
  public List<DevisDto> listerDevisClient(int idClient) {
    try {

      dal.startTransaction();
      return this.devisdao.obtenirSesDevis(idClient);
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }

  @Override
  public DevisDto consulterDevisEnTantQueOuvrier(int idDevis) {
    // TODO Auto-generated method stub
    try {

      dal.startTransaction();

      DevisDto devis = devisdao.consulterDevisEnTantQuOuvrier(idDevis);

      if (devis == null) {
        throw new BizException("Erreur dans la requête");
      }

      return devis;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }



  @Override
  public DevisDto consulterDevisEnTantQueClient(int clientId, int idDevis) {
    // TODO Auto-generated method stub
    try {

      dal.startTransaction();

      DevisDto devis = devisdao.consulterDevisEnTantQueUtilisateur(clientId, idDevis);

      if (devis == null) {
        throw new BizException("Erreur dans la requête");
      }

      return devis;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }



  @Override
  public DevisDto insererDevis(DevisDto devis, int idClient, int[] amenagements, String[] photos) {
    try {
      dal.startTransaction();
      ClientDto client = clientDao.getClientById(idClient);
      if (client == null) {
        throw new BizException("Client inexistant");
      }
      devis.setClient(client);
      devis.setAmenagements(new ArrayList<AmenagementDto>());
      for (int i = 0; i < amenagements.length; i++) {
        AmenagementDto amenagement = amenagementDao.getAmenagementById(amenagements[i]);
        if (amenagement == null) {
          throw new BizException("Amenagement inexistant");
        }
        devis.getAmenagements().add(amenagement);
      }
      return devisdao.insererDevis(devis, photos);
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }

  public boolean changerEtatDevis(int idDevis, String newEtat) {
    try {
      dal.startTransaction();
      String etatActuel = devisdao.getEtatActuel(idDevis);
      System.out.println(etatActuel);
      switch (etatActuel) {
        case "Introduit":
          if (newEtat.equalsIgnoreCase("Commande Confirmée")
              || newEtat.equalsIgnoreCase("Date Confirmée")) {
            devisdao.changerEtatDevis(idDevis, newEtat);
            return true;
          }
          break;
        case "Commande confirmée":
          System.out.println("commande confirmee");
          if (newEtat.equalsIgnoreCase("Date Confirmée")) {
            devisdao.changerEtatDevis(idDevis, newEtat);
            return true;
          }
          break;
        default:
          System.out.println("Changement impossible.");
          return false;
      }
      return false;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }



}

package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.AmenagementDao;
import be.ipl.pae.persistance.dao.ClientDao;
import be.ipl.pae.persistance.dao.DevisDao;

import java.util.ArrayList;
import java.util.List;



class DevisUccImpl implements DevisUcc {

  @Inject
  private DalServices dal;
  @Inject
  private DevisDao devisdao;
  @Inject
  private ClientDao clientDao;
  @Inject
  private AmenagementDao amenagementDao;
  @Inject
  private DtoFactory dtoFact;

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


  public List<DevisDto> listerDevisDUnCLient(int idClient) {

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
  public DevisDto consulterDevisEnTantQueUtilisateur(int clientId, int idDevis) {
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

  public void changerEtatDevis(int idDevis, String newEtat) {
    try {
      dal.startTransaction();
      String etatActuel = devisdao.getEtatActuel(idDevis);

      if (etatActuel == null) {
        throw new BizException("Le devis n'existe pas");
      }
      System.out.println(etatActuel);
      switch (etatActuel) {
        case "Devis introduit":
          if (newEtat.equalsIgnoreCase("Commande confirmée")) {
            devisdao.changerEtatDevis(idDevis, newEtat);
            return;
          }
          break;
        case "Commande confirmée":
          if (newEtat.equalsIgnoreCase("Acompte payé")) {
            devisdao.changerEtatDevis(idDevis, newEtat);
            return;
          }
          break;
        case "Acompte payé":
          if (newEtat.equalsIgnoreCase("Facture milieu chantier envoyée")) {
            devisdao.changerEtatDevis(idDevis, newEtat);
            return;
          }
          break;
        case "Facture milieu chantier envoyée":
          if (newEtat.equalsIgnoreCase("Facture de décompte envoyée")) {
            devisdao.changerEtatDevis(idDevis, newEtat);
            return;
          }
          break;
      }
      throw new BizException("Changement impossible.");
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }


  @Override
  public PhotoDto insererPhotoApresAmenagement(String strPhoto, int idAmenagement, int idDevis,
      boolean visible, boolean preferee) {
    try {
      dal.startTransaction();
      AmenagementDto amenagement = amenagementDao.getAmenagementById(idAmenagement);
      if (amenagement == null) {
        throw new BizException("Amenagement inexistant");
      }
      DevisDto devis = devisdao.obtenirDevisById(idDevis);
      if (devis == null) {
        throw new BizException("Devis inexistant");
      }

      if (preferee && devis.getPhotoPreferee().getPhotoId() != 0) {
        throw new BizException("Le devis posséde déjà une photo préférée");
      }
      AmenagementDto amenagementPhoto = devis.getAmenagements().stream()
          .filter(a -> a.getId() == idAmenagement).findAny().orElse(null);

      if (amenagementPhoto == null) {
        throw new BizException(
            "L'aménagament de la photo ne fait pas partie des aménagement du devis");
      }
      PhotoDto photo = dtoFact.getPhotoDto();
      photo.setAmenagement(amenagement);
      photo.setDevis(devis);
      photo.setPhoto(strPhoto);
      photo.setPreferee(preferee);
      photo.setVisible(visible);
      PhotoDto newPhoto = devisdao.insererPhotoApresAmenagement(photo);
      return newPhoto;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }

  }


  @Override
  public List<String> listerNomsClients(String nom) {
    try {
      dal.startTransaction();

      List<String> nomsClients = devisdao.rechercherNomsClients(nom);
      return nomsClients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }


  @Override
  public List<String> listerAmenagementsTousLesClients(String amenagement) {
    // TODO Auto-generated method stub
    try {
      dal.startTransaction();

      List<String> amenagements = devisdao.rechercherAmenagementsTousLesClients(amenagement);
      return amenagements;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }



}

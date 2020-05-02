package be.ipl.pae.bizz.biz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.AmenagementDao;
import be.ipl.pae.persistance.dao.ClientDao;
import be.ipl.pae.persistance.dao.DevisDao;

import java.time.LocalDate;
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

  @Override
  public List<DevisDto> listerTousLesDevis() {
    try {
      dal.startTransaction();

      List<DevisDto> devis = this.devisdao.obtenirTousLesDevis();

      dal.commitTransaction();
      return devis;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }

  }

  @Override
  public List<DevisDto> listerDevisDUnCLient(int idClient) {

    try {

      dal.startTransaction();

      ClientDto client = this.clientDao.getClientById(idClient);
      if (client == null) {
        throw new BizException("le client n'existe pas");
      }

      List<DevisDto> devis = this.devisdao.obtenirSesDevis(idClient);

      dal.commitTransaction();
      return devis;
    } catch (Exception exception) {

      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }

  }


  @Override
  public DevisDto consulterDevisEnTantQueOuvrier(int idDevis) {
    // TODO Auto-generated method stub
    try {

      dal.startTransaction();

      DevisDto devis = devisdao.consulterDevisEnTantQuOuvrier(idDevis);
      dal.commitTransaction();

      if (devis == null) {
        throw new BizException("Erreur dans la requête");
      }


      return devis;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }

  }



  @Override
  public DevisDto consulterDevisEnTantQueUtilisateur(int clientId, int idDevis) {

    try {

      dal.startTransaction();

      DevisDto devis = devisdao.consulterDevisEnTantQueUtilisateur(clientId, idDevis);


      if (devis == null) {
        throw new BizException("Erreur dans la requête");
      }

      dal.commitTransaction();
      return devis;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
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
      DevisDto newDevis = devisdao.insererDevis(devis, photos);
      dal.commitTransaction();
      return newDevis;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerNomsClients(String nom) {
    try {
      dal.startTransaction();

      List<String> nomsClients = devisdao.rechercherNomsClients(nom);

      dal.commitTransaction();
      return nomsClients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<String> listerPrenomsClients(String prenom) {
    try {
      dal.startTransaction();

      List<String> prenomsClients = devisdao.rechercherPrenomsClients(prenom);

      dal.commitTransaction();
      return prenomsClients;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }



  @Override
  public List<String> listerAmenagementsTousLesClients(String amenagement) {
    try {
      dal.startTransaction();

      List<String> amenagements =
          devisdao.rechercherAmenagementsDesDevisDeTousLesClients(amenagement);

      dal.commitTransaction();
      return amenagements;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }


  @Override
  public List<DevisDto> listerTousLesDevisAffine(String nomClient, String prenomClient,
      String typeAmenagement, String dateDevis, int montantMin, int montantMax) {
    try {
      dal.startTransaction();

      List<DevisDto> devis = devisdao.rechercherTousLesDevisAffine(nomClient, prenomClient,
          typeAmenagement, dateDevis, montantMin, montantMax);

      dal.commitTransaction();
      return devis;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }


  @Override
  public List<String> listerAmenagementsRecherches(String amenagement, int clientId) {
    try {
      dal.startTransaction();

      List<String> amenagements =
          devisdao.rechercherAmenagementsDesDevisDUnClient(amenagement, clientId);

      dal.commitTransaction();
      return amenagements;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }


  @Override
  public List<DevisDto> listerMesDevisAffine(int clientId, String typeAmenagement, String dateDevis,
      int montantMin, int montantMax) {
    try {
      dal.startTransaction();

      List<DevisDto> devis = devisdao.rechercherMesDevisAffine(clientId, typeAmenagement, dateDevis,
          montantMin, montantMax);

      dal.commitTransaction();
      return devis;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public void changerEtatDevis(int idDevis, String newEtat) {
    try {
      dal.startTransaction();
      DevisDto devis = devisdao.obtenirDevisById(idDevis);

      if (devis == null) {
        throw new BizException("Le devis n'existe pas");
      }

      String etatActuel = devis.getEtat();

      if (Etat.ANNULE.getEtat().equalsIgnoreCase(newEtat)) {
        if (Etat.INTRODUIT.getEtat().equalsIgnoreCase(etatActuel)
            || Etat.COMMANDE_CONFIRMEE.getEtat().equalsIgnoreCase(etatActuel)
            || Etat.ACOMPTE_PAYE.getEtat().equalsIgnoreCase(etatActuel)) {
          devisdao.changerEtatDevis(idDevis, newEtat);
        } else {
          throw new BizException("L'état ne peut pas être modifié");
        }
      } else if (Etat.COMMANDE_CONFIRMEE.getEtat().equalsIgnoreCase(etatActuel)) {
        if (Etat.ACOMPTE_PAYE.getEtat().equalsIgnoreCase(newEtat)) {
          devisdao.changerEtatDevis(idDevis, newEtat);
        } else {
          throw new BizException("L'état ne peut pas être modifié");
        }
      } else if (Etat.ACOMPTE_PAYE.getEtat().equalsIgnoreCase(etatActuel)) {
        if (Etat.FACTURE_MILIEU_CHANTIER.getEtat().equalsIgnoreCase(newEtat)
            || Etat.FACTURE_FIN_CHANTIER.getEtat().equalsIgnoreCase(newEtat)) {
          devisdao.changerEtatDevis(idDevis, newEtat);
        } else {
          throw new BizException("L'état ne peut pas être modifié");
        }
      } else if (Etat.FACTURE_MILIEU_CHANTIER.getEtat().equalsIgnoreCase(etatActuel)) {
        if (Etat.FACTURE_FIN_CHANTIER.getEtat().equalsIgnoreCase(newEtat)) {
          devisdao.changerEtatDevis(idDevis, newEtat);
        } else {
          System.out.println("la");
          throw new BizException("L'état ne peut pas être modifié");
        }
      } else if (Etat.FACTURE_FIN_CHANTIER.getEtat().equalsIgnoreCase(etatActuel)) {
        if (Etat.VISIBLE.getEtat().equalsIgnoreCase(newEtat)) {
          devisdao.changerEtatDevis(idDevis, newEtat);
        } else {
          throw new BizException("L'état ne peut pas être modifié");
        }
      } else if (Etat.VISIBLE.getEtat().equalsIgnoreCase(etatActuel)) {
        throw new BizException("L'état ne peut pas être modifié");
      } else {
        throw new BizException("L'état n'est pas valable");
      }

      dal.commitTransaction();
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public void confirmerCommandeAmenagement(int idDevis, LocalDate date) {
    try {
      dal.startTransaction();
      DevisDto devis = devisdao.obtenirDevisById(idDevis);

      if (devis == null) {
        throw new BizException("Le devis n'existe pas");
      }

      String etatActuel = devis.getEtat();
      if (Etat.INTRODUIT.getEtat().equalsIgnoreCase(etatActuel)) {
        devisdao.confirmerCommandeAmenagement(idDevis, date);
      } else {
        throw new BizException("L'état ne peut pas être modifié");
      }
      dal.commitTransaction();
    } catch (Exception exception) {
      exception.printStackTrace();
      dal.rollbackTransaction();
      throw exception;
    }
  }

  @Override
  public void repousserDate(int devisId, LocalDate date) {
    try {
      dal.startTransaction();
      DevisDto devis = devisdao.obtenirDevisById(devisId);
      if (devis == null) {
        throw new BizException("Devis inexistant");
      }
      if (!devis.getEtat().equalsIgnoreCase(Etat.COMMANDE_CONFIRMEE.getEtat())
          && !devis.getEtat().equalsIgnoreCase(Etat.ABSENCE_PAYEMENT_ACOMPTE.getEtat())) {
        throw new BizException("Vous ne pouvez pas repousser la date de début des travaux");
      }

      if (devis.getDateDebut() != null && date.isBefore(devis.getDateDebut())
          && date.isEqual(devis.getDateDebut())) {
        throw new BizException("La date doit être postérieure à la date du début des travaux.");
      }

      if (date.isBefore(devis.getDateDevis()) || devis.getDateDevis().isEqual(date)) {
        throw new BizException("La date doit être postérieure à la date du devis.");
      }

      devisdao.repousserDateTravaux(devisId, date);

      dal.commitTransaction();
    } catch (Exception exception) {
      exception.printStackTrace();
      dal.rollbackTransaction();
      throw exception;
    }
  }


  @Override
  public void supprimerDateDebutTravaux(int idDevis) {

    try {
      dal.startTransaction();
      DevisDto devis = devisdao.obtenirDevisById(idDevis);

      if (devis == null) {
        throw new BizException("Devis inexistant");
      }

      if (!devis.getEtat().equalsIgnoreCase(Etat.COMMANDE_CONFIRMEE.getEtat())) {
        throw new BizException("Vous ne pouvez pas supprimer la date de début des travaux");
      }


      devisdao.supprimerDateDebutTravaux(idDevis, Etat.ABSENCE_PAYEMENT_ACOMPTE.getEtat());
      dal.commitTransaction();

    } catch (Exception exception) {
      exception.printStackTrace();
      dal.rollbackTransaction();
      throw exception;
    }

  }



}

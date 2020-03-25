package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.ClientDao;
import be.ipl.pae.persistance.dao.DevisDao;

import java.util.List;

class DevisUccImpl implements DevisUcc {

  @Inject
  private DevisDao devisdao;
  @Inject
  private DalServices dal;
  @Inject
  private ClientDao clientDao;

  public List<DevisDto> listerTousLesDevis(int idUser) {
    try {
      dal.startTransaction();
      UserDto user = this.devisdao.obtenirUserAvecId(idUser);
      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits pour effectuer cette action");
      }
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
      UserDto user = this.devisdao.obtenirUserAvecId(idUser);
      if (user.getClientId() == 0) {
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
  public List<DevisDto> listerDevisClient(int idUser, int idClient) {
    try {
      dal.startTransaction();
      UserDto user = this.devisdao.obtenirUserAvecId(idUser);
      if (!user.isOuvrier()) {
        throw new BizException("Vous n'avez pas les droits");
      }

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
  public DevisDto consulterDevis(int idUser, int idDevis) {
    // TODO Auto-generated method stub
    try {
      dal.startTransaction();
      UserDto user = this.devisdao.obtenirUserAvecId(idUser);

      DevisDto devis = null;

      if (user.isOuvrier()) {
        devis = devisdao.consulterDevisEnTantQuOuvrier(idDevis);
      } else {
        devis = devisdao.consulterDevisEnTantQueUtilisateur(user.getClientId(), idDevis);
      }


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
  public DevisDto insererDevis(DevisDto devis, int idClient, List<Integer> amenagements,
      List<String> photos) {
    try {
      dal.startTransaction();
      ClientDto client = clientDao.getClientById(idClient);
      if (client == null) {
        throw new BizException("Client inexistant");
      }
      devis.setClient(client);
      return devisdao.insererDevis(devis, idClient, photos);
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    } finally {
      dal.commitTransaction();
    }
  }

}

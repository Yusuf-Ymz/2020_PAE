package be.ipl.pae.bizz.biz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.PhotoUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.AmenagementDao;
import be.ipl.pae.persistance.dao.DevisDao;
import be.ipl.pae.persistance.dao.PhotoDao;

import java.util.List;

public class PhotoUccImpl implements PhotoUcc {
  @Inject
  private PhotoDao photoDao;
  @Inject
  private DtoFactory dtoFact;
  @Inject
  private DevisDao devisdao;
  @Inject
  private AmenagementDao amenagementDao;

  @Inject
  private DalServices dal;

  @Override
  public List<PhotoDto> listerPhotoCarrousel() {
    try {

      dal.startTransaction();
      List<PhotoDto> photos = photoDao.recupererLesPhotosVisible();
      dal.commitTransaction();
      return photos;

    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }

  @Override
  public List<PhotoDto> listerPhotoParAmenagement(int idAmenagement) {
    try {
      dal.startTransaction();
      AmenagementDto amenagement = amenagementDao.getAmenagementById(idAmenagement);

      if (amenagement == null) {
        throw new BizException("Amenagement inexistant");
      }
      List<PhotoDto> photos = photoDao.recupererLesPhotosVisibleParAmenagement(idAmenagement);
      dal.commitTransaction();

      return photos;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
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
      if (!devis.getEtat().equalsIgnoreCase(Etat.VISIBLE.getEtat())) {
        throw new BizException("Vous ne pouvez pas ajouter de photos après aménagements");
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
      PhotoDto newPhoto = photoDao.insererPhotoApresAmenagement(photo);

      dal.commitTransaction();
      return newPhoto;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }

  }
}

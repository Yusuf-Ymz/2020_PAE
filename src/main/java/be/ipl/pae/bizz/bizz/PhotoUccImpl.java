package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.ucc.PhotoUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.AmenagementDao;
import be.ipl.pae.persistance.dao.PhotoDao;

import java.util.List;

public class PhotoUccImpl implements PhotoUcc {
  @Inject
  private PhotoDao photoDao;

  @Inject
  private AmenagementDao amenagementDao;

  @Inject
  private DalServices dal;

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
}

package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.ucc.PhotoUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.PhotoDao;

import java.util.List;

public class PhotoUccImpl implements PhotoUcc {
  @Inject
  private PhotoDao photoDao;

  @Inject
  private DalServices dal;

  public List<PhotoDto> listerPhotoCarrousel() throws Exception {
    // TODO Auto-generated method stub
    try {

      dal.startTransaction();

      List<PhotoDto> photos = photoDao.recupererLesPhotosVisible();

      if (photos == null) {
        throw new BizException("Erreur dans la requête");
      }
      dal.commitTransaction();
      return photos;
    } catch (Exception exception) {
      dal.rollbackTransaction();
      exception.printStackTrace();
      throw exception;
    }
  }
}

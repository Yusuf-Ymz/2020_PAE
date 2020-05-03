package mock.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.persistance.dao.PhotoDao;

import java.util.ArrayList;
import java.util.List;

public class MockPhotoDao implements PhotoDao {

  @Inject
  private DtoFactory dtoFactory;

  @Override
  public List<PhotoDto> recupererLesPhotosVisible() {
    List<PhotoDto> photos = new ArrayList<PhotoDto>();
    for (int i = 0; i < 3; i++) {
      PhotoDto photo = dtoFactory.getPhotoDto();
      photo.setVisible(true);
      photos.add(photo);
    }
    return photos;
  }

  @Override
  public List<PhotoDto> recupererLesPhotosVisibleParAmenagement(int idAmenagement) {
    List<PhotoDto> photos = new ArrayList<PhotoDto>();
    if (idAmenagement == 1) {
      for (int i = 0; i < 3; i++) {
        PhotoDto photo = dtoFactory.getPhotoDto();
        photos.add(photo);
      }
    }
    return photos;
  }

  @Override
  public PhotoDto insererPhotoApresAmenagement(PhotoDto photo) {
    return photo;
  }

}

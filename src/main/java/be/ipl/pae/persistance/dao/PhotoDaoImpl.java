package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalBackendServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDaoImpl extends DaoUtils implements PhotoDao {
  @Inject
  private DalBackendServices dal;
  @Inject
  private DtoFactory fact;

  public List<PhotoDto> recupererLesPhotosVisible() {
    try {
      String queryPhotoAvant = "SELECT * FROM pae.photos p WHERE p.visible = true";
      PreparedStatement prepareStatement = dal.createStatement(queryPhotoAvant);

      ResultSet rs = prepareStatement.executeQuery();

      List<PhotoDto> photos = new ArrayList<PhotoDto>();
      while (rs.next()) {
        PhotoDto photo = fact.getPhotoDto();
        fillObject(photo, rs);
        photos.add(photo);
      }

      return photos;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }
}

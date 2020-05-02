package be.ipl.pae.persistance.dao;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
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

  @Override
  public List<PhotoDto> recupererLesPhotosVisible() {
    try {
      String queryPhotoAvant = "SELECT * FROM pae.photos p, pae.types_amenagements a "
          + "WHERE p.visible = true AND p.type_amenagement = a.type_amenagement";
      PreparedStatement prepareStatement = dal.createStatement(queryPhotoAvant);

      ResultSet rs = prepareStatement.executeQuery();

      List<PhotoDto> photos = new ArrayList<PhotoDto>();
      while (rs.next()) {
        PhotoDto photo = fact.getPhotoDto();
        fillObject(photo, rs);
        AmenagementDto amenagement = fact.getAmenagementDto();
        fillObject(amenagement, rs);
        photo.setAmenagement(amenagement);
        photos.add(photo);
      }

      return photos;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }

  @Override
  public List<PhotoDto> recupererLesPhotosVisibleParAmenagement(int idAmenagement) {
    String query = "SELECT * FROM pae.photos p, pae.types_amenagements a "
        + "WHERE p.visible = true AND p.type_amenagement = a.type_amenagement "
        + "AND a.type_amenagement = ?";
    try {
      PreparedStatement prepareStatement = dal.createStatement(query);
      prepareStatement.setInt(1, idAmenagement);
      ResultSet rs = prepareStatement.executeQuery();

      List<PhotoDto> photos = new ArrayList<PhotoDto>();
      while (rs.next()) {
        PhotoDto photo = fact.getPhotoDto();
        fillObject(photo, rs);
        AmenagementDto amenagement = fact.getAmenagementDto();
        fillObject(amenagement, rs);
        photo.setAmenagement(amenagement);
        photos.add(photo);
      }

      return photos;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
  }

  @Override
  public PhotoDto insererPhotoApresAmenagement(PhotoDto photo) {
    String query = "INSERT INTO pae.photos VALUES (DEFAULT,?,TRUE,?,?,?) RETURNING photo_id";
    PreparedStatement ps = dal.createStatement(query);
    try {
      ps.setString(1, photo.getPhoto());
      ps.setBoolean(2, photo.isVisible());
      ps.setInt(3, photo.getAmenagement().getId());
      ps.setInt(4, photo.getDevis().getDevisId());
      ResultSet rs = ps.executeQuery();
      PhotoDto newPhoto = null;
      if (rs.next()) {
        int idPhoto = rs.getInt(1);
        if (photo.isPreferee()) {
          insererPhotoPreferee(photo.getDevis().getDevisId(), idPhoto);
        }
        newPhoto = getPhotoById(idPhoto);
      }
      return newPhoto;

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }


  private void insererPhotoPreferee(int devisId, int idPhoto) {
    String query = "UPDATE pae.devis SET photo_preferee = ? WHERE devis_id = ?";
    PreparedStatement ps = dal.createStatement(query);
    try {
      ps.setInt(1, idPhoto);
      ps.setInt(2, devisId);
      ps.execute();
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }

  private PhotoDto getPhotoById(int idPhoto) {
    String query = "SELECT * FROM pae.photos WHERE photo_id = ?";
    PreparedStatement ps = dal.createStatement(query);
    PhotoDto photo = fact.getPhotoDto();
    try {
      ps.setInt(1, idPhoto);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        fillObject(photo, rs);
        return photo;
      }
      return null;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }

}

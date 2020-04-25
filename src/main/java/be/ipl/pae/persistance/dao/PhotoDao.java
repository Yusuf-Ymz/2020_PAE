package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.PhotoDto;

import java.sql.SQLException;
import java.util.List;

public interface PhotoDao {

  List<PhotoDto> recupererLesPhotosVisible() throws SQLException;
}

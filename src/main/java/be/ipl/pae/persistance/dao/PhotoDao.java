package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.PhotoDto;

import java.util.List;

public interface PhotoDao {

  List<PhotoDto> recupererLesPhotosVisible();

  List<PhotoDto> recupererLesPhotosVisibleParAmenagement(int idAmenagement);
}

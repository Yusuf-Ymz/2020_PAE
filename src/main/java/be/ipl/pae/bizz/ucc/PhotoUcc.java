package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.PhotoDto;

import java.util.List;

public interface PhotoUcc {

  List<PhotoDto> listerPhotoCarrousel();

  List<PhotoDto> listerPhotoParAmenagement(int idAmenagement);

}

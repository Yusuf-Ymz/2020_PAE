package be.ipl.pae.bizz.biz;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.PhotoUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PhotoUccTest {
  private PhotoUcc photoUcc;
  private AmenagementDto amenagementDto;
  private PhotoDto photo;
  private DtoFactory dtoFactory;

  private

  @BeforeEach void setUp() throws Exception {
    Config.load("test.properties");
    InjectionService injecSvc = new InjectionService();
    this.photoUcc = new PhotoUccImpl();
    injecSvc.injectDependencies(photoUcc);
    this.amenagementDto = null;
    this.photo = null;
    this.dtoFactory = new DtoFactoryImpl();
  }

  @Test
  void testListerPhotoCarrouselOk() {
    assertNotNull(photoUcc.listerPhotoCarrousel());
  }

  @Test
  void testListerPhotoCarrouselPhotosVisible() {
    List<PhotoDto> photos = photoUcc.listerPhotoCarrousel();
    assertTrue(photos.size() == 3);
    for (int i = 0; i < photos.size(); i++) {
      assertTrue(photos.get(i).isVisible());
    }
  }

  @Test
  void testListerPhotoParAmenagementOk() {
    int idAmenagement = 1;
    List<PhotoDto> photos = photoUcc.listerPhotoParAmenagement(idAmenagement);
    assertNotNull(photos);
    assertTrue(photos.size() == 3);
  }

  @Test
  void testListerPhotoParAmenagementAmenagementNonExistant() {
    assertThrows(BizException.class, () -> photoUcc.listerPhotoParAmenagement(11));
  }

  @Test
  void testInsererPhotoApresAmenagementAmenagementNonExistant() {
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 11, 1, false, false));
  }

  @Test
  void testInsererPhotoApresAmenagementDevisNonExistant() {
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, -1, false, false));
  }

  @Test
  void testInsererPhotoApresAmenagementDevisVerificationEtats() {
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, 1, false, false));
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, 2, false, false));
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, 3, false, false));
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, 4, false, false));
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, 5, false, false));
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, 7, false, false));
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, 8, false, false));
  }

  @Test
  void testInsererPhotoApresAmenagementOk() {
    assertNotNull(photoUcc.insererPhotoApresAmenagement("photo", 1, 9, false, false));
  }

  @Test
  void testInsererPhotoApresAmenagementDejaPhotoPrefere() {
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, 10, false, true));
  }

  @Test
  void testInsererPhotoApresAmenagementDejaPhotoPasAmenagementDevis() {
    assertThrows(BizException.class,
        () -> photoUcc.insererPhotoApresAmenagement("photo", 1, 11, false, false));
  }
}


package be.ipl.pae.bizz.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class AmenagementUccTest {
  @Inject
  private DtoFactory dtoFactory;

  private AmenagementUccImpl amenagementUcc;

  @BeforeEach
  void setUp() throws Exception {
    Config.load("test.properties");
    amenagementUcc = new AmenagementUccImpl();
    InjectionService inject = new InjectionService();
    inject.injectDependencies(amenagementUcc);
    this.dtoFactory = new DtoFactoryImpl();
  }

  @Test
  void testListAmenagements() {
    List<AmenagementDto> amenagementsList = amenagementUcc.listerTousLesAmenagements();
    assertNotNull(amenagementsList);
    assertEquals(amenagementsList.size(), 3);
  }

  @Test
  void testAjouterAmenagement() {
    assertNotNull(amenagementUcc.ajouterAmenagement(dtoFactory.getAmenagementDto()));

  }

}

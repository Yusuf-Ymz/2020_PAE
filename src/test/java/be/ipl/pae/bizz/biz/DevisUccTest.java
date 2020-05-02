package be.ipl.pae.bizz.biz;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DevisUccTest {
  private DevisUcc devisUcc;
  private DevisDto devis;
  private DtoFactory dtoFactory;

  @BeforeEach
  void setUp() throws Exception {
    Config.load("test.properties");
    InjectionService injecSvc = new InjectionService();
    this.devisUcc = new DevisUccImpl();
    injecSvc.injectDependencies(devisUcc);
    this.devis = null;
    this.dtoFactory = new DtoFactoryImpl();
  }

  @Test
  void testInsererDevisAvecClientInExistant() {
    int idClient = 5;
    int[] amenagements = {0};
    String[] photos = {""};
    assertThrows(BizException.class,
        () -> devisUcc.insererDevis(devis, idClient, amenagements, photos));
  }

  @Test
  void testInsererDevisSansAmenagements() {
    int idClient = 1;
    int[] amenagements = {11};
    String[] photos = {""};
    devis = dtoFactory.getDevisDto();
    assertThrows(BizException.class,
        () -> devisUcc.insererDevis(devis, idClient, amenagements, photos));
  }

  @Test
  void testInsererDevisSansPhotos() {
    int idClient = 1;
    int[] amenagements = {1};
    String[] photos = {};
    devis = dtoFactory.getDevisDto();
    assertNotNull(devisUcc.insererDevis(devis, idClient, amenagements, photos));
  }

  @Test
  void testInsererDevisOk() {
    int idClient = 1;
    int[] amenagements = {1};
    String[] photos = {"test"};
    devis = dtoFactory.getDevisDto();
    assertNotNull(devisUcc.insererDevis(devis, idClient, amenagements, photos));
  }

  @Test
  void testListerTousLesDevisOk() {
    assertNotNull(devisUcc.listerTousLesDevis());
  }

  @Test
  void testListerDevisDUnClientVide() {
    assertTrue(devisUcc.listerDevisDUnCLient(2).size() == 0);
  }

  @Test
  void testListerDevisDUnClientPasVide() {
    assertTrue(devisUcc.listerDevisDUnCLient(1).size() == 1);
  }

  @Test
  void testConsulterDevisEnTantQueOuvrierException() {
    assertThrows(BizException.class, () -> devisUcc.consulterDevisEnTantQueOuvrier(-1));
  }

  @Test
  void testConsulterDevisEnTantQueOuvrierOk() {
    assertNotNull(devisUcc.consulterDevisEnTantQueOuvrier(1));
  }

  @Test
  void testConsulterDevisEnTantQueUtilisateurException() {
    assertThrows(BizException.class, () -> devisUcc.consulterDevisEnTantQueUtilisateur(2, 1));
  }

  @Test
  void testConsulterDevisEnTantQueUtilisateurOk() {
    assertNotNull(devisUcc.consulterDevisEnTantQueUtilisateur(1, 1));
  }


  @Test
  void testChangerEtatDevisException() {
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(-1, null));
  }

  @Test
  void testChangerEtatDevisIntroduitKo() {
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(1, "Commande confirmée"));
  }

  @Test
  void testChangerEtatDevisIntroduitOk() {
    // assertDoesNotThrow(() -> devisUcc.changerEtatDevis(3, "Commande confirmée"));
  }

  @Test
  void testChangerEtatDevisCommandeConfirmeeKo() {
    // assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(2, "Commande confirmée"));
  }

  @Test
  void testChangerEtatDevisCommandeConfirmeeOk() {
    // assertDoesNotThrow(() -> devisUcc.changerEtatDevis(3, "Date début confirmée"));
  }

  @Test
  void testListerNomsClientsOk() {
    assertTrue(devisUcc.listerNomsClients("test").size() > 0);
  }

  @Test
  void testListerNomsClientsVide() {
    assertTrue(devisUcc.listerNomsClients("yusuf").size() == 0);
  }

  @Test
  void testListerAmenagementsRecherchesOk() {
    assertTrue(devisUcc.listerAmenagementsRecherches("test", 1).size() > 0);
  }

  @Test
  void testListerAmenagementsRecherchesVide() {
    assertTrue(devisUcc.listerAmenagementsRecherches("test", 2).size() == 0);
  }

  @Test
  void testlisterAmenagementsTousLesClientsOk() {
    assertTrue(devisUcc.listerAmenagementsTousLesClients("test").size() > 0);
  }

  @Test
  void testlisterAmenagementsTousLesClientsVide() {
    assertTrue(devisUcc.listerAmenagementsTousLesClients("yusuf").size() == 0);
  }

  @Test
  void testlisterMesDevisAffine() {
    assertTrue(devisUcc.listerMesDevisAffine(1, "test", null, -1, -1).size() > 0);
    assertTrue(devisUcc.listerMesDevisAffine(1, "test", "28/10/2015", -1, -1).size() == 0);
    assertTrue(devisUcc.listerMesDevisAffine(1, "test", "28/10/2015", 100, -1).size() == 0);
    assertTrue(devisUcc.listerMesDevisAffine(1, "test", "28/10/2015", 100, 1000).size() == 0);


    assertTrue(devisUcc.listerMesDevisAffine(2, null, null, -1, 1000).size() > 0);
    assertTrue(devisUcc.listerMesDevisAffine(3, null, null, 100, -1).size() > 0);
    assertTrue(devisUcc.listerMesDevisAffine(4, null, "28/10/2015", -1, -1).size() > 0);
    assertTrue(devisUcc.listerMesDevisAffine(5, "test", "28/10/2015", -1, -1).size() > 0);
    assertTrue(devisUcc.listerMesDevisAffine(6, "test", "28/10/2015", 100, -1).size() > 0);
    assertTrue(devisUcc.listerMesDevisAffine(7, "test", "28/10/2015", 100, 1000).size() > 0);
  }

  @Test
  void testlisterlisterTousLesDevisAffine() {
    assertTrue(devisUcc.listerTousLesDevisAffine("yusuf", null, null, null, -1, -1).size() > 0);
    assertTrue(devisUcc.listerTousLesDevisAffine("yusuf", null, "test", null, -1, -1).size() == 0);
    assertTrue(
        devisUcc.listerTousLesDevisAffine("yusuf", null, "test", "28/10/2015", -1, -1).size() == 0);
    assertTrue(devisUcc.listerTousLesDevisAffine("yusuf", null, "test", "28/10/2015", 100, -1)
        .size() == 0);
    assertTrue(devisUcc.listerTousLesDevisAffine("yusuf", null, "test", "28/10/2015", 100, 1000)
        .size() == 0);



    assertTrue(devisUcc.listerTousLesDevisAffine(null, null, "test", null, -1, -1).size() > 0);
    assertTrue(devisUcc.listerTousLesDevisAffine(null, null, null, null, -1, 1000).size() > 0);
    assertTrue(devisUcc.listerTousLesDevisAffine(null, null, null, null, 100, -1).size() > 0);
    assertTrue(
        devisUcc.listerTousLesDevisAffine(null, null, null, "28/10/2015", -1, -1).size() > 0);
    assertTrue(
        devisUcc.listerTousLesDevisAffine("baptiste", "Dupont", null, null, -1, -1).size() > 0);
    assertTrue(devisUcc.listerTousLesDevisAffine("baptiste", "Dupont", "basses-tiges", null, -1, -1)
        .size() > 0);
    assertTrue(
        devisUcc.listerTousLesDevisAffine("paul", "Dupont", "basses-tiges", "28/10/2015", -1, -1)
            .size() > 0);
    assertTrue(devisUcc
        .listerTousLesDevisAffine("charles", "Dupont", "basses-tiges", "28/10/2015", 100, -1)
        .size() > 0);
    assertTrue(devisUcc
        .listerTousLesDevisAffine("albert", "Dupont", "basses-tiges", "28/10/2015", 100, 1000)
        .size() > 0);
  }
}
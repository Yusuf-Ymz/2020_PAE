package be.ipl.pae.bizz.biz;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

import java.time.LocalDate;

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

  /*
   * @Test void testChangerEtatDevisIntroduitOk() { assertDoesNotThrow( () ->
   * devisUcc.confirmerCommandeAmenagement(1, LocalDate.parse("2020-01-01"))); assertDoesNotThrow(()
   * -> devisUcc.changerEtatDevis(1, "Annulé")); }
   */

  @Test
  void testChangerEtatDevisIntroduitKo() {
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(1, "Devis Introduit"));
    assertThrows(BizException.class, () -> devisUcc.supprimerDateDebutTravaux(1));
    assertThrows(BizException.class, () -> devisUcc.repousserDate(1, null));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(1, "Acompte payé"));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(1, "Facture de milieu chantier envoyée"));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(1, "Facture de fin de chantier envoyée"));

    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(1, "Visible"));
  }

  @Test
  void testChangerEtatDevisCommandeConfirmeeOk() {
    assertDoesNotThrow(() -> devisUcc.changerEtatDevis(2, "Acompte payé"));
    assertDoesNotThrow(() -> devisUcc.changerEtatDevis(2, "Annulé"));
    assertDoesNotThrow(() -> devisUcc.supprimerDateDebutTravaux(2));
    assertDoesNotThrow(() -> devisUcc.repousserDate(2, LocalDate.parse("2020-02-01")));
  }



  @Test
  void testChangerEtatDevisCommandeConfirmeeKo() {
    assertThrows(BizException.class, () -> devisUcc.confirmerCommandeAmenagement(2, null));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(2, "Commande confirmée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(2, "Devis introduit"));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(2, "Facture de milieu chantier envoyée"));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(2, "Facture de fin de chantier envoyée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(2, "Visible"));
    assertThrows(BizException.class,
        () -> devisUcc.repousserDate(2, LocalDate.parse("2020-01-01")));
    assertThrows(BizException.class,
        () -> devisUcc.repousserDate(2, LocalDate.parse("2004-01-01")));

  }


  @Test
  void testChangerEtatDevisAcomptePayeOk() {
    assertDoesNotThrow(() -> devisUcc.changerEtatDevis(3, "Facture de milieu chantier envoyée"));
    assertDoesNotThrow(() -> devisUcc.changerEtatDevis(3, "Facture de fin de chantier envoyée"));
    assertDoesNotThrow(() -> devisUcc.changerEtatDevis(3, "Annulé"));
  }


  @Test
  void testChangerEtatDevisAcomptePayeKo() {
    assertThrows(BizException.class, () -> devisUcc.confirmerCommandeAmenagement(3, null));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(3, "Devis Introduit"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(3, "Commande confirmée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(3, "Acompte payé"));
    assertThrows(BizException.class, () -> devisUcc.supprimerDateDebutTravaux(3));
    assertThrows(BizException.class, () -> devisUcc.repousserDate(3, null));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(3, "Visible"));
  }

  @Test
  void testChangerEtatDevisFactureMilieuOk() {
    assertDoesNotThrow(() -> devisUcc.changerEtatDevis(4, "Facture de fin de chantier envoyée"));
  }



  @Test
  void testChangerEtatDevisFactureMilieuKo() {
    assertThrows(BizException.class, () -> devisUcc.confirmerCommandeAmenagement(4, null));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(4, "Facture de milieu chantier envoyée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(4, "Devis Introduit"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(4, "Commande confirmée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(4, "Acompte payé"));
    assertThrows(BizException.class, () -> devisUcc.supprimerDateDebutTravaux(4));
    assertThrows(BizException.class, () -> devisUcc.repousserDate(4, null));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(4, "Visible"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(4, "Annulé"));
  }


  @Test
  void testChangerEtatDevisFactureFinOk() {
    assertDoesNotThrow(() -> devisUcc.changerEtatDevis(5, "Visible"));
  }


  @Test
  void testChangerEtatDevisFactureFinKo() {
    assertThrows(BizException.class, () -> devisUcc.confirmerCommandeAmenagement(5, null));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(5, "Facture de milieu chantier envoyée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(5, "Devis Introduit"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(5, "Commande confirmée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(5, "Acompte payé"));
    assertThrows(BizException.class, () -> devisUcc.supprimerDateDebutTravaux(5));
    assertThrows(BizException.class, () -> devisUcc.repousserDate(5, null));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(5, "Annulé"));
  }



  @Test
  void testChangerEtatDevisVisibleKo() {
    assertThrows(BizException.class, () -> devisUcc.confirmerCommandeAmenagement(6, null));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(6, "Devis Introduit"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(6, "Commande confirmée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(6, "Acompte payé"));
    assertThrows(BizException.class, () -> devisUcc.supprimerDateDebutTravaux(6));
    assertThrows(BizException.class, () -> devisUcc.repousserDate(6, null));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(6, "Facture de milieu chantier envoyée"));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(6, "Facture de fin de chantier envoyée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(6, "Visible"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(6, "Annulé"));

  }


  @Test
  void testChangerEtatDevisAbsenceOk() {
    assertDoesNotThrow(() -> devisUcc.repousserDate(7, LocalDate.parse("2020-01-01")));
  }


  @Test
  void testChangerEtatDevisAbsenceKo() {
    assertThrows(BizException.class, () -> devisUcc.confirmerCommandeAmenagement(7, null));
    assertThrows(BizException.class,
        () -> devisUcc.repousserDate(7, LocalDate.parse("2018-01-01")));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(7, "Devis Introduit"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(7, "Commande confirmée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(7, "Acompte payé"));
    assertThrows(BizException.class, () -> devisUcc.supprimerDateDebutTravaux(7));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(7, "Facture de milieu chantier envoyée"));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(7, "Facture de fin de chantier envoyée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(7, "Visible"));

  }



  @Test
  void testChangerEtatDevisAnnuleKo() {
    assertThrows(BizException.class, () -> devisUcc.confirmerCommandeAmenagement(8, null));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(8, "Devis Introduit"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(8, "Commande confirmée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(8, "Acompte payé"));
    assertThrows(BizException.class, () -> devisUcc.supprimerDateDebutTravaux(8));
    assertThrows(BizException.class, () -> devisUcc.repousserDate(8, null));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(8, "Facture de milieu chantier envoyée"));
    assertThrows(BizException.class,
        () -> devisUcc.changerEtatDevis(8, "Facture de fin de chantier envoyée"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(8, "Visible"));
    assertThrows(BizException.class, () -> devisUcc.changerEtatDevis(8, "Annulé"));
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
  void testListerPrenomsClientsOk() {
    assertTrue(devisUcc.listerPrenomsClients("test").size() > 0);
  }

  @Test
  void testListerPrenomsClientVide() {
    assertTrue(devisUcc.listerPrenomsClients("yusuf").size() == 0);
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

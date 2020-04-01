package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DevisUccTest {
  private DevisUcc devisUcc;
  private DevisDto devis;

  @BeforeEach
  void setUp() throws Exception {
    Config.load("test.properties");
    InjectionService injecSvc = new InjectionService();
    this.devisUcc = new DevisUccImpl();
    injecSvc.injectDependencies(devisUcc);
    this.devis = null;
  }


  @Test
  void testListerTousLesDevisOk() {
    assertNotNull(devisUcc.listerTousLesDevis());
  }

  @Test
  void testListerDevisDUnClientVide() {
    // TODO Auto-generated method stub
    assertTrue(devisUcc.listerDevisDUnCLient(2).size() == 0);
  }

  @Test
  void testListerDevisDUnClientPasVide() {
    // TODO Auto-generated method stub
    assertTrue(devisUcc.listerDevisDUnCLient(1).size() == 1);
  }

  @Test
  void testConsulterDevisEnTantQueOuvrierException() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.consulterDevisEnTantQueOuvrier(-1));
  }

  @Test
  void testConsulterDevisEnTantQueOuvrierOk() {
    // TODO Auto-generated method stub
    assertNotNull(devisUcc.consulterDevisEnTantQueOuvrier(1));
  }

  @Test
  void testConsulterDevisEnTantQueUtilisateurException() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.consulterDevisEnTantQueUtilisateur(2, 1));
  }

  @Test
  void testConsulterDevisEnTantQueUtilisateurOk() {
    // TODO Auto-generated method stub
    assertNotNull(devisUcc.consulterDevisEnTantQueUtilisateur(1, 1));
  }
}

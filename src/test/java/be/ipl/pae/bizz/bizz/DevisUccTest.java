package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    System.out.println(devisUcc.getClass());
    injecSvc.injectDependencies(devisUcc);
    this.devis = null;
  }

  @Test
  void testListerTousLesDevisException() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }

  @Test
  void testListerTousLesDevisClient() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }

  @Test
  void testListerTousLesDevisOuvrier() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }

  @Test
  void testListerMesDevisException() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }

  @Test
  void testListerMesDevisOk() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }

  @Test
  void testListerDevisClientException() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }

  @Test
  void testListerDevisClientOk() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }

  @Test
  void testConsulterDevisException() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }

  @Test
  void testConsulterDevisOuvrier() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }

  @Test
  void testConsulterDevisClient() {
    // TODO Auto-generated method stub
    assertThrows(BizException.class, () -> devisUcc.listerTousLesDevis(-1));
  }
}

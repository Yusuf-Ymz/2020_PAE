package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserUccTest {
  private UserUcc ucc;

  @BeforeEach
  void setUp() throws Exception {
    Config.load("test.properties");
    InjectionService injecSer = new InjectionService();
    ucc = new UserUccImpl();
    injecSer.injectDependencies(ucc);
  }



  @Test
  public void testUccNotNull() {
    assertNotNull(ucc);
  }


  @Test()
  public void testSeConnecterMauvaisMdp() {
    try {
      ucc.seConnecter("pseudo", "faux");
    } catch (Exception exception) {
      assertTrue(exception instanceof BizException);
    }

  }

  @Test
  public void testSeConnecterMauvaisPseudo() {
    try {
      ucc.seConnecter("faux", "mdp");
    } catch (Exception exception) {
      assertTrue(exception instanceof BizException);
    }
  }



  /*
   * @Test public void testSeConnecterOk() { assertNotNull(ucc.seConnecter("pseudo", "azerty")); }
   */



}

package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserUccTest {
  private DtoFactory dtoFactory;
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


  @Test
  public void testSeConnecterMauvaisMdp() {
    assertNull(ucc.seConnecter("pseudo", "faux"));
  }

  @Test
  public void testSeConnecterMauvaisPseudo() {
    assertNull(ucc.seConnecter("faux", "mdp"));
  }

  @Test
  public void testSeConnecterOk() {
    assertNotNull(ucc.seConnecter("pseudo", "mdp"));
  }

}

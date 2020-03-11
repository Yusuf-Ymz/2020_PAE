package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.main.InjectionService;
import be.ipl.pae.persistance.dao.UserDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserUccTest {
  private DtoFactory dtoFactory;
  private UserUcc ucc;

  @BeforeEach
  void setUp() throws Exception {
    InjectionService injecSer = new InjectionService("test.properties");
    dtoFactory = new DtoFactoryImpl();
    UserDao userDao = injecSer.getDependency(UserDao.class, dtoFactory);
    ucc = new UserUccImpl(userDao);
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

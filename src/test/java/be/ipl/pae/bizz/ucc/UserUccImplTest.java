package be.ipl.pae.bizz.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.ipl.pae.bizz.bizz.DtoFactory;
import be.ipl.pae.bizz.bizz.DtoFactoryImpl;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.main.InjectionService;
import be.ipl.pae.persistance.dal.DalService;
import be.ipl.pae.persistance.dao.UserDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserUccImplTest {
  private DtoFactory dtoFactory;
  private UserUcc ucc;

  @BeforeEach
  void setUp() throws Exception {
    InjectionService injecSer = new InjectionService("test.properties");
    DalService dalService = injecSer.getDependency(DalService.class);
    dtoFactory = new DtoFactoryImpl();
    UserDao userDao = injecSer.getDependency(UserDao.class, dalService, dtoFactory);
    ucc = new UserUccImpl(userDao);
  }


  @Test
  public void testUccNoteNull() {
    assertNotNull(ucc);
  }

  @Test
  public void testSeConnecterMauvaisMdp() {
    UserDto usr = dtoFactory.getUserDto();
    usr.setPassword("faux");
    usr.setPseudo("pseudo");
    assertNull(ucc.seConnecter(usr));
  }

  @Test
  public void testSeConnecterMauvaisPseudo() {
    UserDto usr = dtoFactory.getUserDto();
    usr.setPassword("mdp");
    usr.setPseudo("faux");
    assertNull(ucc.seConnecter(usr));
  }

  @Test
  public void testSeConnecterOk() {
    UserDto usr = dtoFactory.getUserDto();
    usr.setPassword("mdp");
    usr.setPseudo("pseudo");
    assertNotNull(ucc.seConnecter(usr));
  }

}

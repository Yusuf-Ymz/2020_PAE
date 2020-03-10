package be.ipl.pae.bizz.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import be.ipl.pae.bizz.bizz.DtoFactory;
import be.ipl.pae.main.InjectionService;
import be.ipl.pae.persistance.dal.DalService;
import be.ipl.pae.persistance.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserUccImplTest {

  private UserUcc ucc;

  @BeforeEach
  void setUp() throws Exception {
    InjectionService injecSer = new InjectionService("test.properties"); // TODO créer fichier
                                                                         // properties !
    DalService dalService = injecSer.getDependency(DalService.class);
    DtoFactory dtoFactory = injecSer.getDependency(DtoFactory.class);
    UserDao userDao = injecSer.getDependency(UserDao.class, dalService, dtoFactory);
    ucc = new UserUccImpl(userDao);
  }

  @Test
  void test() {
    assertNotNull(ucc);
  }

}

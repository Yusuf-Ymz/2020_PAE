package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.bizz.dto.UserDto;
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


  @Test
  public void testSeConnecterMauvaisMdp() {
    assertThrows(BizException.class, () -> ucc.seConnecter("pseudo", "faux"));

  }

  @Test
  public void testSeConnecterMauvaisPseudo() {
    assertThrows(BizException.class, () -> ucc.seConnecter("faux", "mdp"));
  }

  @Test
  public void testSeConnecterOk() {
    assertNotNull(ucc.seConnecter("pseudo", "azerty"));
  }

  // Pour créer ce test je dois avoir un dtoFactoryMock je pense pour récupérer un user
  // @Test
  // public void testInscriptionOk() {
  // UserDto user = dtoFactory.getUserDto();
  // user.setEmail("email");
  // user.setPseudo("pseudo");
  // ucc.inscrire(user);
  // assertTrue(user != null);
  // }
  //
  // @Test
  // public void testInscritpionPseudoDouble() {
  // UserDto user = dtoFactory.getUserDto();
  // user.setEmail("email");
  // user.setPseudo(" ");
  // assertThrows(BizException.class,() -> ucc.inscrire(user));
  // }
  //
  // @Test
  // public void testInscritpionEmailDouble() {
  // UserDto user = dtoFactory.getUserDto();
  // user.setEmail(" ");
  // user.setPseudo("pseudo");
  // assertThrows(BizException.class,() -> ucc.inscrire(user));
  // }


  @Test
  public void testListerUserOk() {
    int userId = 1;
    assertNotNull(ucc.listerUsers(userId));
  }

  @Test
  public void testListerUsersPreinscritOk() {
    int userId = 1;
    assertNotNull(ucc.listerUsersPreinscrit(userId));
  }

  @Test
  public void testConfirmUserOk() {
    int idClient = 2;
    int idUser = 3;
    int idOuvrier = 1;
    UserDto user = ucc.confirmUser(idOuvrier, idUser, idClient);
    assertNotNull(user);
    assertTrue(user.isConfirme());
    assertEquals(user.getClientId(), 2);
  }

  @Test
  public void testConfirmWorkerOk() {
    int idOuvrier = 1;
    int idUser = 2;
    UserDto user = ucc.confirmWorker(idOuvrier, idUser);
    assertNotNull(user);
    assertTrue(user.isConfirme());
    assertTrue(user.isOuvrier());
  }

  @Test
  public void testTrouverInfoUtilisateurOk() {
    int idOuvrier = 1;
    int idUser = 2;
    assertNotNull(ucc.trouverInfoUtilisateur(idOuvrier, idUser));
  }
}

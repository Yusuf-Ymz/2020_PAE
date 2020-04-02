package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserUccTest {
  private UserUcc ucc;

  @Inject
  private DtoFactory dtoFactory;

  @BeforeEach
  void setUp() throws Exception {
    Config.load("test.properties");
    InjectionService injecSer = new InjectionService();
    ucc = new UserUccImpl();
    injecSer.injectDependencies(ucc);
    dtoFactory = new DtoFactoryImpl();
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

  @Test
  public void testSeConnecterUserPasConfirme() {
    assertThrows(BizException.class, () -> ucc.seConnecter("nonConfirme", "azerty"));
  }

  @Test
  public void testInscriptionOk() {
    UserDto user = dtoFactory.getUserDto();
    user.setEmail("email");
    user.setPseudo("pseudo");
    ucc.inscrire(user);
    assertNotNull(user);
  }

  @Test
  public void testInscritpionPseudoDouble() {
    UserDto user = dtoFactory.getUserDto();
    user.setEmail("email");
    user.setPseudo(" ");
    assertThrows(BizException.class, () -> ucc.inscrire(user));
  }

  @Test
  public void testInscritpionEmailDouble() {
    UserDto user = dtoFactory.getUserDto();
    user.setEmail(" ");
    user.setPseudo("pseudo");
    assertThrows(BizException.class, () -> ucc.inscrire(user));
  }

  @Test
  public void testListerUserOk() {
    int userId = 1;
    assertNotNull(ucc.listerUsers());
  }

  @Test
  public void testListerUsersPreinscritOk() {
    int userId = 1;
    assertNotNull(ucc.listerUsersPreinscrit());
  }


  @Test
  public void testConfirmUserOk() {
    int idClient = 2;
    int idUser = 5;
    UserDto user = ucc.confirmUser(idUser, idClient);
    assertNotNull(user);
    assertTrue(user.isConfirme());
    assertEquals(user.getClientId(), 2);
  }

  @Test
  public void testConfirmUserDejaConfirme() {
    int idClient = 2;
    int idUser = 3;
    assertThrows(BizException.class, () -> ucc.confirmUser(idUser, idClient));
  }

  @Test
  public void testConfirmWorkerOk() {
    int idUser = 2;
    UserDto user = ucc.confirmWorker(idUser);
    assertNotNull(user);
    assertTrue(user.isConfirme());
    assertTrue(user.isOuvrier());
  }

  @Test
  public void testConfirmWorkerDejaConfirme() {
    int idUser = 3;
    assertThrows(BizException.class, () -> ucc.confirmWorker(idUser));
  }

  @Test
  public void testObtenirUtilisateurOk() {
    int idUser = 2;
    assertNotNull(ucc.obtenirUtilisateur(idUser));
  }

  @Test
  public void testObtenirUtilisateurNull() {
    int idUser = 10;
    assertThrows(BizException.class, () -> ucc.obtenirUtilisateur(idUser));
  }


}

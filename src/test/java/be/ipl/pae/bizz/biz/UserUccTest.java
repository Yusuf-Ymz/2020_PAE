package be.ipl.pae.bizz.biz;

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
  void testSeConnecterMauvaisPseudoBonMdp() {
    assertThrows(BizException.class, () -> ucc.seConnecter("mauvaisPseudo", "azerty"));
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
  public void testInscriptionPseudoDouble() {
    UserDto user = dtoFactory.getUserDto();
    user.setEmail("email");
    user.setPseudo(" ");
    assertThrows(BizException.class, () -> ucc.inscrire(user));
  }

  @Test
  public void testInscriptionEmailDouble() {
    UserDto user = dtoFactory.getUserDto();
    user.setEmail(" ");
    user.setPseudo("pseudo");
    assertThrows(BizException.class, () -> ucc.inscrire(user));
  }

  @Test
  public void testListerUserOk() {
    assertNotNull(ucc.listerUsers());
  }

  @Test
  public void testListerUsersPreinscritOk() {
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

  @Test
  public void testlisterNomsUtilisateursOk() {
    assertTrue(ucc.listerNomsUtilisateurs("test").size() > 0);
  }

  @Test
  public void testlisterNomsUtilisateursVide() {
    assertTrue(ucc.listerNomsUtilisateurs("yusuf").size() == 0);
  }

  @Test
  public void testlisterPrenomsUtilisateursOk() {
    assertTrue(ucc.listerPrenomsUtilisateurs("test").size() > 0);
  }

  @Test
  public void testlisterPrenomsUtilisateursVide() {
    assertTrue(ucc.listerNomsUtilisateurs("yusuf").size() == 0);
  }

  @Test
  public void testlisterVillesUtilisateursOk() {
    assertTrue(ucc.listerVillesUtilisateurs("test").size() > 0);
  }

  @Test
  public void testlisterVillesUtilisateursVide() {
    assertTrue(ucc.listerVillesUtilisateurs("yusuf").size() == 0);
  }

  @Test
  public void testlisterUtilisateursAvecCriteres() {
    assertTrue(ucc.listerUtilisateursAvecCriteres("yilmaz", null, null).size() > 0);
    assertTrue(ucc.listerUtilisateursAvecCriteres("yilmaz", "yusuf", null).size() == 0);
    assertTrue(ucc.listerUtilisateursAvecCriteres("yilmaz", "yusuf", "paris").size() == 0);


    assertTrue(ucc.listerUtilisateursAvecCriteres("yaffa", "elie", null).size() > 0);
    assertTrue(ucc.listerUtilisateursAvecCriteres("okou", "gnakouri", "bxl").size() > 0);
  }


  @Test
  public void testlisterNomsUtilisateursNonConfirmeOk() {
    assertTrue(ucc.listerNomsUtilisateursNonConfirme("test").size() > 0);
  }

  @Test
  public void testlisterNomsUtilisateursNonConfirmeVide() {
    assertTrue(ucc.listerNomsUtilisateursNonConfirme("yusuf").size() == 0);
  }

  @Test
  public void testlisterPrenomsUtilisateursNonConfirmeOk() {
    assertTrue(ucc.listerPrenomsUtilisateursNonConfirme("test").size() > 0);
  }

  @Test
  public void testlisterPrenomsUtilisateursNonConfirmeVide() {
    assertTrue(ucc.listerNomsUtilisateursNonConfirme("yusuf").size() == 0);
  }

  @Test
  public void testlisterVillesUtilisateursNonConfirmeOk() {
    assertTrue(ucc.listerVillesUtilisateursNonConfirme("test").size() > 0);
  }

  @Test
  public void testlisterVillesUtilisateursNonConfirmeVide() {
    assertTrue(ucc.listerVillesUtilisateursNonConfirme("yusuf").size() == 0);
  }

  @Test
  public void testlisterUtilisateursNonConfirmeAvecCriteres() {
    assertTrue(ucc.listerUtilisateursNonConfirmeAvecCriteres("yilmaz", null, null).size() > 0);
    assertTrue(ucc.listerUtilisateursNonConfirmeAvecCriteres("yilmaz", "yusuf", null).size() == 0);
    assertTrue(
        ucc.listerUtilisateursNonConfirmeAvecCriteres("yilmaz", "yusuf", "paris").size() == 0);

    assertTrue(ucc.listerUtilisateursNonConfirmeAvecCriteres("yaffa", "elie", null).size() > 0);
    assertTrue(ucc.listerUtilisateursNonConfirmeAvecCriteres("okou", "gnakouri", "bxl").size() > 0);

  }


}

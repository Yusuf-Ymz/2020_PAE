package be.ipl.pae.bizz.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ClientUccTest {

  @Inject
  private DtoFactory dtoFactory;
  private ClientUcc clientUcc;

  @BeforeEach
  void setUp() throws Exception {
    Config.load("test.properties");
    InjectionService injecSvc = new InjectionService();
    this.clientUcc = new ClientUccImpl();
    System.out.println(clientUcc.getClass());
    injecSvc.injectDependencies(clientUcc);
    dtoFactory = new DtoFactoryImpl();
  }

  @Test
  void testInsertClientOk() {
    ClientDto nextClient = dtoFactory.getClientDto();
    nextClient.setIdClient(1);
    nextClient.setNom("nom1");
    nextClient.setNumero("prenom1");
    ClientDto clientInsert = clientUcc.insertClient(nextClient);

    assertNotNull(clientInsert);
    assertEquals(1, clientInsert.getIdClient());

  }


  @Test
  void testListerClientsOk() {

    List<ClientDto> liste = clientUcc.listerClients();
    assertNotNull(liste);
    assertEquals(3, liste.size());

  }

  @Test
  void testListerNomsClientsOk() {
    List<String> liste = clientUcc.listerNomsClients("so");
    assertNotNull(liste);
    assertEquals(2, liste.size());
  }


  @Test
  void testListerPrenomsClientsOk() {

    List<String> liste = clientUcc.listerPrenomsClients("so");
    assertNotNull(liste);
    assertEquals(2, liste.size());

  }

  @Test
  void testListerVillesOk() {
    List<String> liste = this.clientUcc.listerVilles("br");
    assertNotNull(liste);
    assertEquals(2, liste.size());
  }



  @Test
  void testListerCpOk() {
    assertNotNull(this.clientUcc.listerCp("10"));
    assertEquals(2, this.clientUcc.listerCp("10").size());
  }

  @Test
  void testListerClientsAvecCriteresOk() {
    assertTrue(this.clientUcc.listerClientsAvecCriteres("Terieur", "Alex", "Bruxelles", "1070")
        .size() == 1);
    assertTrue(
        this.clientUcc.listerClientsAvecCriteres("Terieur", "Alain", "Liege", "4031").size() == 1);
    assertTrue(this.clientUcc.listerClientsAvecCriteres("Croche", "Sara", "Bruxelles", "1020")
        .size() == 1);
    assertTrue(this.clientUcc.listerClientsAvecCriteres("Menvussa", "Gerard", "Namur", "5000")
        .size() == 1);
    assertTrue(this.clientUcc.listerClientsAvecCriteres("Voir", "Rihanna", "Bruxelles", "1000")
        .size() == 1);

  }

  @Test
  void testListerClientsAvecCriteresVide() {
    assertEquals(0,
        this.clientUcc.listerClientsAvecCriteres("nom1", "prenom1", "ville1", "cp1").size());

  }

  @Test
  void testlisterClientNonLieAvecCriteresOk() {
    this.testListerClientsAvecCriteresOk();
  }

  @Test
  void testlisterClientNonLieAvecCriteresVide() {
    this.testListerClientsAvecCriteresVide();
  }


  @Test
  void testListerNomsClientsNonlieOk() {
    this.testListerNomsClientsOk();
  }


  @Test
  void testListerPrenomsClientsNonLieOk() {
    this.testListerPrenomsClientsOk();
  }

  @Test
  void testListerVillesNonLieOk() {
    this.testListerVillesOk();
  }

  @Test
  void testListerCpNonLieOk() {
    this.testListerCpOk();
  }


  @Test
  void testListerClientsPasUtilisateur() {

    assertNotNull(this.clientUcc.listerClientsPasUtilisateur());
    assertEquals(5, this.clientUcc.listerClientsPasUtilisateur().size());

  }


}

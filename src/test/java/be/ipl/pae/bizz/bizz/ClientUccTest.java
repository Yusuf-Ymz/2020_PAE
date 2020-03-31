package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ClientUccTest {


  private ClientUcc clientUcc;
  private ClientDto client;

  @BeforeEach
  void setUp() throws Exception {
    Config.load("test.properties");
    InjectionService injecSvc = new InjectionService();
    this.clientUcc = new ClientUccImpl();
    System.out.println(clientUcc.getClass());
    injecSvc.injectDependencies(clientUcc);
    this.client = null;
  }

  @Test
  void testInsertClientException() {

  }


  @Test
  void testListerClientsOk() {

    List<ClientDto> liste = clientUcc.listerClients();
    assertNotNull(liste);

  }

  @Test
  void testListerClientsException() {



  }



  @Test
  void testListerNomsClientsException() {



  }

  void testListerNomsClientsOk() {


    assertNotNull(clientUcc.listerNomsClients("so"));
  }



  @Test
  void testListerPrenomsClientsException() {


  }


  @Test
  void testListerPrenomsClientsOk() {

    assertNotNull(clientUcc.listerPrenomsClients("so"));

  }

  @Test
  void testListerVillesException() {


  }

  @Test
  void testListerVillesOk() {

    assertNotNull(this.clientUcc.listerVilles("br"));


  }



  @Test
  void testListerCp() {
    // TODO
  }


  @Test
  void testListerClientsAvecFiltreException() {

  }

  void testListerClientsAvecFiltreOk() {
    // TODO
  }

  @Test
  void testListerClientsPasUtilisateurException() {


  }

  @Test
  void testListerClientsPasUtilisateur() {

    assertNotNull(this.clientUcc.listerVilles("bru"));

  }

}

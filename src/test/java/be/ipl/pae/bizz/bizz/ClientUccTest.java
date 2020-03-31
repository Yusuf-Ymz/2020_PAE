package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.exception.BizException;
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

    assertThrows(BizException.class, () -> {
      this.clientUcc.insertClient(client, 5);
    });
  }


  @Test
  void testListerClientsOk() {

    List<ClientDto> liste = clientUcc.listerClients(1);
    assertNotNull(liste);

  }

  @Test
  void testListerClientsException() {

    assertThrows(BizException.class, () -> {
      this.clientUcc.listerClients(2);
    });


  }



  @Test
  void testListerNomsClientsException() {

    assertThrows(BizException.class, () -> {
      this.clientUcc.listerNomsClients(5, "sc");
    });



  }

  void testListerNomsClientsOk() {


    assertNotNull(clientUcc.listerNomsClients(1, "so"));
  }



  @Test
  void testListerPrenomsClientsException() {

    assertThrows(BizException.class, () -> {
      this.clientUcc.listerPrenomsClients(5, "so");
    });


  }


  @Test
  void testListerPrenomsClientsOk() {

    assertNotNull(clientUcc.listerPrenomsClients(1, "so"));

  }

  @Test
  void testListerVillesException() {

    assertThrows(BizException.class, () -> {
      this.clientUcc.listerVilles(5, "br");
    });
  }

  @Test
  void testListerVillesOk() {

    assertNotNull(this.clientUcc.listerVilles(1, "br"));


  }



  @Test
  void testListerCp() {
    // TODO
  }


  @Test
  void testListerClientsAvecFiltreException() {
    assertThrows(BizException.class, () -> {
      this.clientUcc.listerClientsAvecCriteres(5, "nom1", "nom1", "bruxelles", "1080");
    });
  }

  void testListerClientsAvecFiltreOk() {
    // TODO
  }

  @Test
  void testListerClientsPasUtilisateurException() {
    assertThrows(BizException.class, () -> {
      this.clientUcc.listerClientsPasUtilisateur(5);
    });

  }

  @Test
  void testListerClientsPasUtilisateur() {

    assertNotNull(this.clientUcc.listerVilles(1, "bru"));

  }

}

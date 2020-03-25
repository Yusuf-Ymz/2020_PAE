package be.ipl.pae.bizz.bizz;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.main.Config;
import be.ipl.pae.main.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ClientUccTest {


  private ClientUcc clientUcc;

  @BeforeEach
  void setUp() throws Exception {
    Config.load("test.properties");
    InjectionService injecSvc = new InjectionService();
    this.clientUcc = new ClientUccImpl();
    System.out.println(clientUcc.getClass());
    injecSvc.injectDependencies(clientUcc);
  }

  @Test
  void testInsertClient() {

  }

  @Test
  void testListerClientsInt() {

    List<ClientDto> liste = clientUcc.listerClients(1);
    assertNotNull(liste);

    liste = clientUcc.listerClients(2);
    assertNull(liste);

  }

  @Test
  void testListerNomsClients() {

  }

  @Test
  void testListerPrenomsClients() {

  }

  @Test
  void testListerVilles() {

  }

  @Test
  void testListerCp() {

  }

  @Test
  void testListerClientsIntStringStringStringString() {

  }

  @Test
  void testListerClientsPasUtilisateur() {

  }

}

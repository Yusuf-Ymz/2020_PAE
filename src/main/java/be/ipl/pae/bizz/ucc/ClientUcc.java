package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.ClientDto;

import java.util.List;

public interface ClientUcc {

  ClientDto insertClient(ClientDto client, int idOuvrier);

  List<ClientDto> listerClients(int idClient);
}

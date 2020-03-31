package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.ClientDto;

import java.util.List;

public interface ClientUcc {

  ClientDto insertClient(ClientDto client);

  /**
   * Renvoie la liste des clients.
   * 
   * @param idOuvrier : id du client
   * @return une liste des clients
   */
  List<ClientDto> listerClients();

  List<String> listerNomsClients(String nom);

  List<String> listerPrenomsClients(String prenom);

  List<String> listerVilles(String ville);

  List<String> listerCp(String cp);

  List<ClientDto> listerClientsAvecCriteres(String nom, String prenom, String ville, String cp);

  List<ClientDto> listerClientsPasUtilisateur();
}

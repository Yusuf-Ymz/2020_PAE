package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.ClientDto;

import java.util.List;

public interface ClientUcc {

  ClientDto insertClient(ClientDto client, int idOuvrier);

  /**
   * Renvoie la liste des clients.
   * 
   * @param idOuvrier : id du client
   * @return une liste des clients
   */
  List<ClientDto> listerClients(int idOuvrier);

  List<String> listerNomsClients(int idOuvrier, String nom);

  List<String> listerPrenomsClients(int idOuvrier, String prenom);

  List<String> listerVilles(int idOuvrier, String ville);

  List<String> listerCp(int idOuvrier, String cp);

  List<ClientDto> listerClients(int idOuvrier, String nom, String prenom, String ville, String cp);

  List<ClientDto> listerClientsPasUtilisateur(int idUser);
}

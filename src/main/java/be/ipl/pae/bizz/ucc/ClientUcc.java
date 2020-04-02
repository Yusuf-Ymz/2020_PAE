package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.ClientDto;

import java.util.List;

public interface ClientUcc {

  /**
   * Ajoute un client.
   * 
   * @param client : client à ajouter
   * @return une liste des clients
   */
  ClientDto insertClient(ClientDto client);

  /**
   * Renvoie la liste des clients.
   * 
   * @return une liste des clients
   */
  List<ClientDto> listerClients();

  List<String> listerNomsClients(String nom);

  List<String> listerPrenomsClients(String prenom);

  List<String> listerVilles(String ville);

  List<String> listerCp(String cp);

  /**
   * Recherche la liste des clients avec comme critère les paramètres.
   * 
   * @param nom : nom que les clients devront avoir
   * @param prenom : prenom que les clients devront avoir
   * @param ville : ville que les clients devront avoir
   * @param cp : cp que les clients devront avoir
   * @return une liste des clients avec comme critère les paramètres
   */
  List<ClientDto> listerClientsAvecCriteres(String nom, String prenom, String ville, String cp);

  /**
   * Recherche la liste des clients qui ne sont pas liés à un utilisateur
   * 
   * @param nom : nom que les clients devront avoir
   * @return une liste des clients qui ne sont pas liés à un utilisateur
   */
  List<ClientDto> listerClientsPasUtilisateur();
}

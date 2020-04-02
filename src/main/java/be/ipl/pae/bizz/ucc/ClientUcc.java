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

  /**
   * Renvoie les noms des clients sous forme de liste.
   * 
   * @param nom
   * @return la liste des noms
   */
  List<String> listerNomsClients(String nom);

  /**
   * Renvoie les prenoms des clients sous forme de liste.
   * 
   * @param prenom
   * @return la liste des prenoms
   */
  List<String> listerPrenomsClients(String prenom);

  /**
   * Renvoie les villes des clients sous forme de liste.
   * 
   * @param ville
   * @return la liste des villes
   */
  List<String> listerVilles(String ville);

  /**
   * Renvoie les code postaux des clients sous forme de liste.
   * 
   * @param cp
   * @return la liste des code postaux.
   */
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
   * Recherche la liste des clients qui ne sont pas liés à un utilisateur.
   * 
   * @return une liste des clients qui ne sont pas liés à un utilisateur
   */
  List<ClientDto> listerClientsPasUtilisateur();
}

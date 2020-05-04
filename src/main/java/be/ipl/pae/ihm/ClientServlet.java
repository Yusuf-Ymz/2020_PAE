package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.exception.FatalException;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientServlet extends HttpServlet {


  private static final long serialVersionUID = -1254800213292593724L;
  private static final String ACTIONGETCP = "getCP";
  private static final String ACTIONGETNOMS = "getNom";
  private static final String ACTIONGETVILLE = "getVille";
  private static final String KEYWORD = "keyword";
  private static final String ACTIONCLIENTSAFFINE = "listClientsAffine";
  private static final String ACTIONCLIENTSNONLIEAFFINE = "listClientsNonLieAffine";
  private static final String ACTIONGETPRENOM = "getPrenom";
  private static final String ACTIONINSERTCLIENT = "ajouterClient";
  private static final String ACTIONCLIENTPASUTILISATEUR = "listeClientsPasUtilisateur";
  private static final String ACTIONLISTERCLIENTS = "listerClients";
  private static final String ACTIONGETNOMSCLIENTNONLIE = "getNomsClientNonLie";
  private static final String ACTIONGETPRENOMSCLIENTNONLIE = "getPrenomsClientNonLie";
  private static final String ACTIONGETVILLESCLIENTNONLIE = "getVillesClientNonLie";
  private static final String ACTIONGETCPCLIENTNONLIE = "getCPClientNonLie";
  private Genson genson;
  private Genson gensonClient;



  @Inject
  private ClientUcc clientUcc;
  @Inject
  private DtoFactory fact;
  @Inject
  private UserUcc userUcc;

  /**
   * Instancie le client servlet.
   */
  public ClientServlet() {


    this.genson = new Genson();
    this.gensonClient = ServletUtils.getGensonClient();

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String token = req.getHeader("Authorization");
      int idUser = ServletUtils.estConnecte(token);
      String json = "{\"error\":\"Vous n'avez pas accès à ces informations\"}";
      int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
      String action = req.getParameter("action");

      if (idUser != -1 && this.userUcc.obtenirUtilisateur(idUser).isOuvrier()) {

        if (ACTIONGETCP.equalsIgnoreCase(action)) {
          List<String> cpx = clientUcc.listerCp(req.getParameter(KEYWORD));
          json = "{\"cpx\":" + genson.serialize(cpx) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONGETNOMS.equalsIgnoreCase(action)) {
          List<String> noms = clientUcc.listerNomsClients(req.getParameter(KEYWORD));
          json = "{\"names\":" + genson.serialize(noms) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONGETVILLE.equalsIgnoreCase(action)) {
          List<String> villes = clientUcc.listerVilles(req.getParameter(KEYWORD));
          json = "{\"villes\":" + genson.serialize(villes) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONCLIENTSAFFINE.equalsIgnoreCase(action)) {
          String nom = req.getParameter("nom");
          String prenom = req.getParameter("prenom");
          String ville = req.getParameter("ville");
          String cp = req.getParameter("cp");
          List<ClientDto> clients = clientUcc.listerClientsAvecCriteres(nom, prenom, ville, cp);
          json = "{\"clients\":"
              + gensonClient.serialize(clients, new GenericType<List<ClientDto>>() {}) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONGETPRENOM.equalsIgnoreCase(action)) {
          List<String> prenoms = clientUcc.listerPrenomsClients(req.getParameter(KEYWORD));
          json = "{\"prenoms\":" + genson.serialize(prenoms) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONCLIENTPASUTILISATEUR.equalsIgnoreCase(action)) {
          List<ClientDto> clients = clientUcc.listerClientsPasUtilisateur();
          String listeSerialisee =
              this.gensonClient.serialize(clients, new GenericType<List<ClientDto>>() {});
          json = "{\"data\":" + listeSerialisee + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONLISTERCLIENTS.equalsIgnoreCase(action)) {
          listCustomer(resp);// à modifer aussi
        } else if (ACTIONGETNOMSCLIENTNONLIE.equals(action)) {
          List<String> noms = clientUcc.listerNomsClientsNonLie(req.getParameter(KEYWORD));
          json = "{\"names\":" + genson.serialize(noms) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONGETPRENOMSCLIENTNONLIE.equalsIgnoreCase(action)) {
          List<String> prenoms = clientUcc.listerPrenomsClientsNonLie(req.getParameter(KEYWORD));
          json = "{\"prenoms\":" + genson.serialize(prenoms) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONGETVILLESCLIENTNONLIE.equalsIgnoreCase(action)) {
          List<String> villes = clientUcc.listerVillesClientsNonLie(req.getParameter(KEYWORD));
          json = "{\"villes\":" + genson.serialize(villes) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONGETCPCLIENTNONLIE.equalsIgnoreCase(action)) {
          List<String> cpx = clientUcc.listerCpClientsNonLie(req.getParameter(KEYWORD));
          json = "{\"cpx\":" + genson.serialize(cpx) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONCLIENTSNONLIEAFFINE.equals(action)) {
          String nom = req.getParameter("nom");
          String prenom = req.getParameter("prenom");
          String ville = req.getParameter("ville");
          String cp = req.getParameter("cp");
          List<ClientDto> clients =
              clientUcc.listerClientsNonLieAvecCriteres(nom, prenom, ville, cp);
          json = "{\"data\":"
              + gensonClient.serialize(clients, new GenericType<List<ClientDto>>() {}) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        }

      } else {
        ServletUtils.sendResponse(resp, json, statusCode);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      String json = "{\"error\":\"Champs invalides\"}";
      int status = HttpServletResponse.SC_BAD_REQUEST;
      ServletUtils.sendResponse(resp, json, status);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Map<String, Object> body = ServletUtils.decoderBodyJson(req);

    String action = (String) body.get("action");
    String token = req.getHeader("Authorization");
    int idUser = ServletUtils.estConnecte(token);

    String json = "{\"error\":\"Vous n'avez pas accès à ces informations\"}";
    int statusCode = HttpServletResponse.SC_UNAUTHORIZED;

    if (idUser != -1 && this.userUcc.obtenirUtilisateur(idUser).isOuvrier()) {

      if (ACTIONINSERTCLIENT.equalsIgnoreCase(action)) {
        System.out.println("inserer");
        try {
          String err = "{\"error\":\"Champ nom invalide\"}";
          String nom = body.get("nom").toString();
          if (nom.trim().isEmpty()) {
            ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
            return;
          }
          err = "{\"error\":\"Champ prénom invalide \"}";
          String prenom = body.get("prenom").toString();
          if (nom.trim().isEmpty()) {
            ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
            return;
          }
          err = "{\"error\":\"Champ ville invalide\"}";
          String ville = body.get("ville").toString();
          if (ville.trim().isEmpty()) {
            ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
            return;
          }
          err = "{\"error\":\"Champ code postal invalide\"}";
          String cp = body.get("cp").toString();
          System.out.println(cp);
          if (!cp.matches("^[0-9]{4,}$")) {
            ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
            return;
          }
          err = "{\"error\":\"Champ email invalide\"}";
          String email = body.get("email").toString();
          if (!email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,4})+$")) {
            ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
            return;
          }
          err = "{\"error\":\"Champ téléphone invalide\"}";
          String tel = body.get("telephone").toString();
          if (!tel.matches(".{9,}")) {
            ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
            return;
          }
          err = "{\"error\":\"Champ rue invalide\"}";
          String rue = body.get("rue").toString();
          if (!rue.matches("[a-zA-Z]{4,}")) {
            ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
            return;
          }
          err = "{\"error\":\"Champ numéro invalide\"}";
          String numero = body.get("numero").toString();
          if (!numero.matches("[0-9]{1,}")) {
            ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
            return;
          }
          ClientDto client = fact.getClientDto();
          client.setNom(nom);
          client.setPrenom(prenom);
          client.setEmail(email);
          client.setCodePostal(cp);
          client.setTelephone(tel);
          client.setRue(rue);
          client.setVille(ville);
          client.setNumero(numero);
          client = clientUcc.insertClient(client);
          json = "{\"client\":" + genson.serialize(client) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } catch (FatalException exception) {
          exception.printStackTrace();
          String err = "{\"error\":\" Erreur serveur \"}";
          statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
          ServletUtils.sendResponse(resp, err, statusCode);
        }
      }

    } else {
      ServletUtils.sendResponse(resp, json, statusCode);
    }
  }


  /**
   * Gére la requete permettant de lister tous les clients.
   * 
   * @param req : la requete
   * @param resp : la reponse
   * @throws Exception : une exception
   */
  private void listCustomer(HttpServletResponse resp) throws Exception {


    int status = HttpServletResponse.SC_UNAUTHORIZED;

    List<ClientDto> listeClients = this.clientUcc.listerClients();

    String liste = gensonClient.serialize(listeClients, new GenericType<List<ClientDto>>() {});
    String json = "{\"clients\":" + liste + "}";
    status = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, status);

  }
}


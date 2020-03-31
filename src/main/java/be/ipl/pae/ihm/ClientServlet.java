package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.main.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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
  private static final String ACTIONGETPRENOM = "getPrenom";
  private static final String ACTIONINSERTCLIENT = "ajouterClient";
  private static final String ACTIONCLIENTPASUTILISATEUR = "listeClientsPasUtilisateur";
  private static final String ACTIONLISTERCLIENTS = "listerClients";
  private String secret;
  private Genson genson;
  private Genson gensonClient;



  @Inject
  private ClientUcc clientUcc;
  @Inject
  private DtoFactory fact;

  /**
   * Instancie le client servlet.
   */
  public ClientServlet() {

    this.secret = Config.getConfiguration("secret");
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
      if (idUser != -1) {
        if (ACTIONGETCP.equalsIgnoreCase(action)) {
          List<String> cpx = clientUcc.listerCp(idUser, req.getParameter(KEYWORD));
          json = "{\"cpx\":" + genson.serialize(cpx) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONGETNOMS.equalsIgnoreCase(action)) {
          List<String> noms = clientUcc.listerNomsClients(idUser, req.getParameter(KEYWORD));
          json = "{\"names\":" + genson.serialize(noms) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONGETVILLE.equalsIgnoreCase(action)) {
          List<String> villes = clientUcc.listerVilles(idUser, req.getParameter(KEYWORD));
          json = "{\"villes\":" + genson.serialize(villes) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONCLIENTSAFFINE.equalsIgnoreCase(action)) {
          String nom = req.getParameter("nom");
          String prenom = req.getParameter("prenom");
          String ville = req.getParameter("ville");
          String cp = req.getParameter("cp");
          List<ClientDto> clients = clientUcc.listerClientsAvecCriteres(idUser, nom, prenom, ville, cp);
          json = "{\"clients\":" + genson.serialize(clients) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONGETPRENOM.equalsIgnoreCase(action)) {
          List<String> prenoms = clientUcc.listerPrenomsClients(idUser, req.getParameter(KEYWORD));
          json = "{\"prenoms\":" + genson.serialize(prenoms) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONINSERTCLIENT.equalsIgnoreCase(action)) {
          ClientDto client = fact.getClientDto();
          client.setNom(req.getParameter("nom"));
          client.setPrenom(req.getParameter("prenom"));
          // client.setBoite(req.getParameter("boite"));
          client.setEmail(req.getParameter("email"));
          client.setCodePostal(req.getParameter("cp"));
          client.setTelephone(req.getParameter("telephone"));
          client.setRue(req.getParameter("rue"));
          client.setVille(req.getParameter("ville"));
          client.setNumero(req.getParameter("numero"));
          client = clientUcc.insertClient(client, idUser);
          json = "{\"client\":" + genson.serialize(client) + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONCLIENTPASUTILISATEUR.equalsIgnoreCase(action)) {
          List<ClientDto> clients = clientUcc.listerClientsPasUtilisateur(idUser);
          String listeSerialisee =
              this.gensonClient.serialize(clients, new GenericType<List<ClientDto>>() {});
          json = "{\"data\":" + listeSerialisee + "}";
          statusCode = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, statusCode);
        } else if (ACTIONLISTERCLIENTS.equalsIgnoreCase(action)) {
          listCustomer(req, resp);// à modifer aussi
        }
      } else {
        ServletUtils.sendResponse(resp, json, statusCode);
      }
      // this.listCustomer(req, resp);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Map<String, Object> body = ServletUtils.decoderBodyJson(req);
    String action = (String) body.get("action");
    String token = req.getHeader("Authorization");
    int idUser = ServletUtils.estConnecte(token);
    String json = "{\"error\":\"Vous n'avez pas accès à ces informations\"}";
    int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
    if (idUser != -1) {
      if (ACTIONINSERTCLIENT.equalsIgnoreCase(action)) {
        ClientDto client = fact.getClientDto();
        client.setNom((String) body.get("nom"));
        client.setPrenom((String) body.get("prenom"));
        // client.setBoite((String) body.get("boite"));
        client.setEmail((String) body.get("email"));
        client.setCodePostal((String) body.get("cp"));
        client.setTelephone((String) body.get("telephone"));
        client.setRue((String) body.get("rue"));
        client.setVille((String) body.get("ville"));
        client.setNumero((String) body.get("numero"));
        client = clientUcc.insertClient(client, idUser);
        json = "{\"client\":" + genson.serialize(client) + "}";
        statusCode = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, statusCode);
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
  private void listCustomer(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    String token = req.getHeader("Authorization");
    String json = "{\"error\":\"Vous n'avez pas accés ces informations\"}";
    int idUser = ServletUtils.estConnecte(token);
    int status = HttpServletResponse.SC_UNAUTHORIZED;
    if (idUser != -1) {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      int userId = jwt.getClaim("id").asInt();


      List<ClientDto> listeClients = this.clientUcc.listerClients(userId);

      String liste = gensonClient.serialize(listeClients, new GenericType<List<ClientDto>>() {});
      json = "{\"clients\":" + liste + "}";
      status = HttpServletResponse.SC_OK;
      ServletUtils.sendResponse(resp, json, status);

    } else {
      ServletUtils.sendResponse(resp, json, status);
    }


  }
}


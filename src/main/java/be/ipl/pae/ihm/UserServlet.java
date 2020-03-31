package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {


  private static final long serialVersionUID = 1L;
  @Inject
  private UserUcc userUcc;

  private Genson genson;

  /**
   * Instancie un servlet.
   */
  public UserServlet() {
    super();
    this.genson = ServletUtils.getGensonUser();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      System.out.println(req.getParameter("action"));
      if (req.getParameter("action").equals("listeUser")) {
        displayListUsers(req, resp);
      }
      if (req.getParameter("action").equals("confirmerInscription")) {
        displayListUsersPreregistered(req, resp);
      } else if (req.getParameter("action").equals("recupererUtilisateur")) {
        System.out.println("je rentre pas");
        afficherInfoUtilisateur(req, resp);
      }

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void afficherInfoUtilisateur(HttpServletRequest req, HttpServletResponse resp) {
    String token = req.getHeader("Authorization");
    String json = null;
    int ouvrierId = ServletUtils.estConnecte(token);
    if (ouvrierId != -1) {
      int userId = Integer.parseInt(req.getParameter("N° utilisateur").toString());
      UserDto user = userUcc.trouverInfoUtilisateur(ouvrierId, userId);
      System.out.println(user);
      json = "{\"data\":" + genson.serialize(user, new GenericType<UserDto>() {}) + "}";
      int statusCode = HttpServletResponse.SC_OK;
      ServletUtils.sendResponse(resp, json, statusCode);
    } else {
      json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
      int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
      ServletUtils.sendResponse(resp, json, statusCode);
    }
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String token = req.getHeader("Authorization");
      int ouvrierId = ServletUtils.estConnecte(token);
      if (ouvrierId != -1) {
        Genson genson = new Genson();
        Map<String, Object> map = genson.deserialize(req.getReader(), Map.class);
        String action = map.get("action").toString();
        System.out.println(action);
        System.out.println("map" + map.toString());
        if (action.equals("confirmerInscription/worker")) {
          // tu enleveres les commentaires une fois que tu te serviras des variables car ca cause
          // des problemes de check
          // String email = map.get("email").toString();
          System.out.println(map.toString());
          int idConfirmed = Integer.parseInt((String) map.get("N° utilisateur"));
          // String nom = map.get("nom").toString();
          // String prenom = map.get("prenom").toString();
          // String pseudo = map.get("pseudo").toString();
          // String ville = map.get("ville").toString();
          UserDto userDto = userUcc.confirmWorker(ouvrierId, idConfirmed);
          if (userDto == null) {
            String json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
            int status = HttpServletResponse.SC_UNAUTHORIZED;
            ServletUtils.sendResponse(resp, json, status);
          } else {
            String json = "{\"success\": \"true\"}";
            int status = HttpServletResponse.SC_OK;
            ServletUtils.sendResponse(resp, json, status);
          }
        }
        if (action.equals("confirmerInscription/lierUtilisateurClient")) {
          int idUser = Integer.parseInt((String) map.get("idUser"));
          int idClient = Integer.parseInt((String) map.get("N° client"));
          UserDto userDto = userUcc.confirmUser(ouvrierId, idUser, idClient);
          if (userDto == null) {
            String json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
            int status = HttpServletResponse.SC_UNAUTHORIZED;
            ServletUtils.sendResponse(resp, json, status);
          } else {
            String json = "{\"success\": \"true\"}";
            int status = HttpServletResponse.SC_OK;
            ServletUtils.sendResponse(resp, json, status);
          }
        }

      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Gére la requete permettant de lister tous les utilisateurs inscrits.
   * 
   * @param req : la requete
   * @param resp : le resp
   * @throws Exception : une exception
   */
  private void displayListUsers(HttpServletRequest req, HttpServletResponse resp)

      throws IOException {

    String token = req.getHeader("Authorization");
    String json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
    int status = HttpServletResponse.SC_UNAUTHORIZED;

    int userId = ServletUtils.estConnecte(token);
    if (userId != -1) {
      List<UserDto> listeUser = userUcc.listerUsers(userId);
      String listeSerialisee =
          this.genson.serialize(listeUser, new GenericType<List<UserDto>>() {});
      json = "{\"listeUser\":" + listeSerialisee + "}";
      status = HttpServletResponse.SC_OK;
      ServletUtils.sendResponse(resp, json, status);

    } else {
      ServletUtils.sendResponse(resp, json, status);
    }
  }


  /**
   * Gére la requete permettant de lister tous les utilisateurs en demande de confirmations
   * d'inscriptions.
   * 
   * @param req : la requete
   * @param resp : le resp
   * @throws Exception : une exception
   */
  private void displayListUsersPreregistered(HttpServletRequest req, HttpServletResponse resp)

      throws IOException {
    String token = req.getHeader("Authorization");
    System.out.println("toke : " + token);
    String json = null;
    int userId = ServletUtils.estConnecte(token);
    if (userId != -1) {
      List<UserDto> listeUsersPreinscrit = userUcc.listerUsersPreinscrit(userId);
      json = "{\"data\":"
          + genson.serialize(listeUsersPreinscrit, new GenericType<List<UserDto>>() {}) + "}";
      int statusCode = HttpServletResponse.SC_OK;
      ServletUtils.sendResponse(resp, json, statusCode);
    } else {
      json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
      int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
      ServletUtils.sendResponse(resp, json, statusCode);
    }
  }

}

package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.exception.BizException;
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
    String json = "";
    try {

      String token = req.getHeader("Authorization");
      int userId = ServletUtils.estConnecte(token);
      System.out.println(token);
      System.out.println(userId);
      UserDto user = userUcc.obtenirUtilisateur(userId);

      if (userId != -1) {

        if (user.isOuvrier()) {

          if (req.getParameter("action").equals("listeUser")) {
            displayListUsers(resp);
          } else if (req.getParameter("action").equals("confirmerInscription")) {
            System.out.println("lsiter");
            displayListUsersPreregistered(resp);
          } else if (req.getParameter("action").equals("recupererUtilisateur")) {
            afficherInfoUtilisateur(req, resp);
          }

        }

        if (req.getParameter("action").equals("obtenirUser")) {
          json = "{\"user\":" + genson.serialize(user) + "}";
          ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_OK);
        }


      } else {

        json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        ServletUtils.sendResponse(resp, json, statusCode);
      }

    } catch (BizException exception) {
      exception.printStackTrace();
      int status = HttpServletResponse.SC_FORBIDDEN;
      json = "{\"error\":\"" + exception.getMessage() + "\"";
      ServletUtils.sendResponse(resp, json, status);
    } catch (FatalException exception) {
      exception.printStackTrace();
      json = "{\"error\":\"" + exception.getMessage() + "\"";
      int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      ServletUtils.sendResponse(resp, json, status);
    } catch (Exception exception) {
      exception.printStackTrace();
      ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_PRECONDITION_FAILED);
    }
  }

  /**
   * Gère la requête permettant d'aller rechercher les information de l'utilisateur.
   * 
   * @param resp : le resp
   * @throws Exception : une exception
   */
  private void afficherInfoUtilisateur(HttpServletRequest req, HttpServletResponse resp) {


    int userId = Integer.parseInt(req.getParameter("N° utilisateur").toString());
    UserDto user = userUcc.obtenirUtilisateur(userId);

    String json = "{\"data\":" + genson.serialize(user, new GenericType<UserDto>() {}) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);

  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String token = req.getHeader("Authorization");
      int ouvrierId = ServletUtils.estConnecte(token);

      if (ouvrierId != -1 && userUcc.obtenirUtilisateur(ouvrierId).isOuvrier()) {
        Genson genson = new Genson();
        Map<String, Object> map = genson.deserialize(req.getReader(), Map.class);
        String action = map.get("action").toString();
        System.out.println(action);
        System.out.println("map" + map.toString());
        if (action.equals("confirmerInscription/worker")) {
          // String email = map.get("email").toString();
          System.out.println(map.toString());
          int idConfirmed = Integer.parseInt((String) map.get("N° utilisateur"));
          // String nom = map.get("nom").toString();
          // String prenom = map.get("prenom").toString();
          // String pseudo = map.get("pseudo").toString();
          // String ville = map.get("ville").toString();
          UserDto userDto = userUcc.confirmWorker(idConfirmed);
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

          UserDto userDto = userUcc.confirmUser(idUser, idClient);

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
   * @param resp : le resp
   * @throws Exception : une exception
   */
  private void displayListUsers(HttpServletResponse resp)

      throws Exception {

    List<UserDto> listeUser = userUcc.listerUsers();
    String listeSerialisee = this.genson.serialize(listeUser, new GenericType<List<UserDto>>() {});
    String json = "{\"listeUser\":" + listeSerialisee + "}";
    int status = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, status);

  }


  /**
   * Gére la requete permettant de lister tous les utilisateurs en demande de confirmations
   * d'inscriptions.
   *
   * @param resp : le resp
   * @throws Exception : une exception
   */
  private void displayListUsersPreregistered(HttpServletResponse resp)

      throws IOException {

    List<UserDto> listeUsersPreinscrit = userUcc.listerUsersPreinscrit();
    String json = "{\"data\":"
        + genson.serialize(listeUsersPreinscrit, new GenericType<List<UserDto>>() {}) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);

  }

}

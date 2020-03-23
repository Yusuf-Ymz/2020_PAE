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
      if (req.getParameter("action").equals("listeUser")) {
        displayListUsers(req, resp);
      }
      if (req.getParameter("action").equals("confirmerInscription")) {
        displayListUsersPreregistered(req, resp);
      }

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String token = req.getHeader("Authorization");
      int userId = ServletUtils.estConnecte(token);
      if (userId != -1) {
        Genson genson = new Genson();
        Map<String, Object> map = genson.deserialize(req.getReader(), Map.class);
        String email = map.get("email").toString();
        System.out.println(map.toString());
        int idConfirmed = Integer.parseInt((String) map.get("id"));
        String nom = map.get("nom").toString();
        String prenom = map.get("prenom").toString();
        String pseudo = map.get("pseudo").toString();
        String ville = map.get("ville").toString();
        String action = map.get("action").toString();
        System.out.println(action);
        if (action.equals("confirmerInscription/onlyUser")) {
          UserDto userDto = userUcc.confirmUser(userId, idConfirmed);
          if (userDto != null) {
            throw new Error("confirmerInscription/onlyUser");
          }
        }
        if (action.equals("confirmerInscription/worker")) {
          UserDto userDto = userUcc.confirmWorker(userId, idConfirmed);
          if (userDto != null) {
            throw new Error("confirmerInscription/worker");
          }
        }
        if (action.equals("confirmerInscription/lienClient")) {
          userUcc.initilisation();
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
    String json = null;
    int userId = ServletUtils.estConnecte(token);
    if (userId != -1) {
      List<UserDto> listeUser = userUcc.listerUsers(userId);
      String listeSerialisee =
          this.genson.serialize(listeUser, new GenericType<List<UserDto>>() {});
      System.out.println(listeSerialisee);
      json = "{\"listeUser\":" + listeSerialisee + "}";
      // ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_OK);
      resp.setStatus(HttpServletResponse.SC_OK);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.getWriter().write(json);
    } else {
      json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.getWriter().write(json);
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
    String json = null;
    int userId = ServletUtils.estConnecte(token);
    if (userId != -1) {
      List<UserDto> listeUsersPreinscrit = userUcc.listerUsersPreinscrit(userId);
      System.out.println(listeUsersPreinscrit);
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

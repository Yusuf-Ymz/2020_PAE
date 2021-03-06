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
      UserDto user = userUcc.obtenirUtilisateur(userId);

      if (userId != -1) {

        if (user.isOuvrier()) {

          if (req.getParameter("action").equals("listeUser")) {
            displayListUsers(resp);
          } else if (req.getParameter("action").equals("confirmerInscription")) {

            displayListUsersPreregistered(resp);
          } else if (req.getParameter("action").equals("recupererUtilisateur")) {
            afficherInfoUtilisateur(req, resp);
          } else if (req.getParameter("action").equals("listeUtilisateursAffine")) {
            listerUtilisateursAffines(req, resp);
          } else if (req.getParameter("action").equals("getVille")) {
            listerVillesUtilisateurs(req, resp);
          } else if (req.getParameter("action").equals("getNom")) {
            listerNomsUtilisateurs(req, resp);
          } else if (req.getParameter("action").equals("getPrenom")) {
            listerPrenomsUtilisateurs(req, resp);
          } else if (req.getParameter("action").equals("listeUtilisateursNonConfirmeAffine")) {
            listerUtilisateursAffinesNonConfirme(req, resp);
          } else if (req.getParameter("action").equals("getVilleNonConfirme")) {
            listerVillesUtilisateursNonConfirme(req, resp);
          } else if (req.getParameter("action").equals("getNomNonConfirme")) {
            listerNomsUtilisateursNonConfirme(req, resp);
          } else if (req.getParameter("action").equals("getPrenomNonConfirme")) {
            listerPrenomsUtilisateursNonConfirme(req, resp);
          }

        }

        if (req.getParameter("action").equals("obtenirUser")) {
          json = "{\"user\":" + ServletUtils.serializeUser(user) + "}";
          ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_OK);
        }


      } else {

        json = "{\"error\":\"Vous n'avez pas acc??s ?? ces informations\"}";
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        ServletUtils.sendResponse(resp, json, statusCode);
      }

    } catch (BizException exception) {
      exception.printStackTrace();
      int status = HttpServletResponse.SC_FORBIDDEN;
      json = "{\"error\":\"" + exception.getMessage() + "\"}";
      ServletUtils.sendResponse(resp, json, status);
    } catch (FatalException exception) {
      exception.printStackTrace();
      json = "{\"error\":\"" + exception.getMessage() + "\"}";
      int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      ServletUtils.sendResponse(resp, json, status);
    } catch (Exception exception) {
      exception.printStackTrace();
      ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_PRECONDITION_FAILED);
    }
  }

  /**
   * G??re la requ??te permettant d'aller rechercher les information de l'utilisateur.
   * 
   * @param resp : le resp
   * @throws Exception : une exception
   */
  private void afficherInfoUtilisateur(HttpServletRequest req, HttpServletResponse resp) {


    int userId = Integer.parseInt(req.getParameter("N?? utilisateur").toString());
    UserDto user = userUcc.obtenirUtilisateur(userId);

    String json = "{\"data\":" + genson.serialize(user, new GenericType<UserDto>() {}) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);

  }

  private void listerVillesUtilisateurs(HttpServletRequest req, HttpServletResponse resp) {
    String keyword = req.getParameter("keyword");
    List<String> villes = userUcc.listerVillesUtilisateurs(keyword);

    String json = "{\"villes\":" + genson.serialize(villes) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);
  }

  private void listerNomsUtilisateurs(HttpServletRequest req, HttpServletResponse resp) {
    String keyword = req.getParameter("keyword");
    List<String> noms = userUcc.listerNomsUtilisateurs(keyword);

    String json = "{\"noms\":" + genson.serialize(noms) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);
  }

  private void listerPrenomsUtilisateurs(HttpServletRequest req, HttpServletResponse resp) {
    String keyword = req.getParameter("keyword");
    List<String> prenoms = userUcc.listerPrenomsUtilisateurs(keyword);

    String json = "{\"prenoms\":" + genson.serialize(prenoms) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);
  }

  /**
   * Renvoie la liste des utilisateurs correspondant aux crit??res de recherches.
   * 
   * @param req : la requ??te
   * @param resp : la r??ponse
   */
  private void listerUtilisateursAffines(HttpServletRequest req, HttpServletResponse resp) {
    String nom = req.getParameter("nom");
    String prenom = req.getParameter("prenom");
    String ville = req.getParameter("ville");

    List<UserDto> users = userUcc.listerUtilisateursAvecCriteres(nom, prenom, ville);

    String json =
        "{\"listeUser\":" + genson.serialize(users, new GenericType<List<UserDto>>() {}) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);
  }

  private void listerVillesUtilisateursNonConfirme(HttpServletRequest req,
      HttpServletResponse resp) {
    String keyword = req.getParameter("keyword");
    List<String> villes = userUcc.listerVillesUtilisateursNonConfirme(keyword);

    String json = "{\"villes\":" + genson.serialize(villes) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);
  }

  private void listerNomsUtilisateursNonConfirme(HttpServletRequest req, HttpServletResponse resp) {
    String keyword = req.getParameter("keyword");
    List<String> noms = userUcc.listerNomsUtilisateursNonConfirme(keyword);

    String json = "{\"noms\":" + genson.serialize(noms) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);
  }

  private void listerPrenomsUtilisateursNonConfirme(HttpServletRequest req,
      HttpServletResponse resp) {
    String keyword = req.getParameter("keyword");
    List<String> prenoms = userUcc.listerPrenomsUtilisateursNonConfirme(keyword);

    String json = "{\"prenoms\":" + genson.serialize(prenoms) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);
  }

  /**
   * Renvoie la liste des utilisateurs non confirm?? correspondant aux crit??res de recherches.
   * 
   * @param req : la requ??te
   * @param resp : la r??ponse
   */
  private void listerUtilisateursAffinesNonConfirme(HttpServletRequest req,
      HttpServletResponse resp) {
    String nom = req.getParameter("nom");
    String prenom = req.getParameter("prenom");
    String ville = req.getParameter("ville");

    List<UserDto> users = userUcc.listerUtilisateursNonConfirmeAvecCriteres(nom, prenom, ville);

    String json = "{\"data\":" + genson.serialize(users, new GenericType<List<UserDto>>() {}) + "}";
    int statusCode = HttpServletResponse.SC_OK;
    ServletUtils.sendResponse(resp, json, statusCode);
  }


  @SuppressWarnings("unchecked")
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

        if (action.equals("confirmerInscription/worker")) {

          int idConfirmed = Integer.parseInt((String) map.get("N?? utilisateur"));

          UserDto userDto = userUcc.confirmWorker(idConfirmed);
          if (userDto == null) {
            String json = "{\"error\":\"Vous n'avez pas acc??s ?? ces informations\"}";
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
          int idClient = Integer.parseInt((String) map.get("N?? client"));

          UserDto userDto = userUcc.confirmUser(idUser, idClient);

          if (userDto == null) {
            String json = "{\"error\":\"Vous n'avez pas acc??s ?? ces informations\"}";
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
      String json = "{\"error\":\"Champs invalides\"}";
      int status = HttpServletResponse.SC_BAD_REQUEST;
      ServletUtils.sendResponse(resp, json, status);
    }
  }

  /**
   * G??re la requete permettant de lister tous les utilisateurs inscrits.
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
   * G??re la requete permettant de lister tous les utilisateurs en demande de confirmations
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

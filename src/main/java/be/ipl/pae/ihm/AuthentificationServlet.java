package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.main.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthentificationServlet extends HttpServlet {


  private static final long serialVersionUID = 1L;
  @Inject
  private UserUcc userUcc;
  @Inject
  private DtoFactory dtoFactory;
  private Genson genson;
  private String secret;



  /**
   * Instancie l'authentification servlet.
   */
  public AuthentificationServlet() {
    this.secret = Config.getConfiguration("secret");
    this.genson = new Genson();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Map<String, Object> body = ServletUtils.decoderBodyJson(req);
    String action = (String) body.get("action");

    switch (action) {
      case "register":
        try {
          registerUser(resp, body);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
        break;
      case "connection":
        try {
          login(resp, body);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
        break;
      default:
        break;
    }
  }

  /**
   * Gére la requete permettant de connecter un utilisateur.
   * 
   * @param req : la requete
   * @param resp : le resp
   * @throws Exception : une exception
   */
  @SuppressWarnings("unchecked")
  private void login(HttpServletResponse resp, Map<String, Object> body) throws Exception {

    String pseudo = body.get("pseudo").toString();
    String password = body.get("password").toString();

    String json = null;
    int status = HttpServletResponse.SC_UNAUTHORIZED;

    try {
      UserDto user = userUcc.seConnecter(pseudo, password);

      user.setPassword(null);
      String token = JWT.create().withClaim("id", user.getUserId()).sign(Algorithm.HMAC512(secret));
      json = "{\"token\":\"" + token + "\",\"user\":" + genson.serialize(user) + "}";
      status = HttpServletResponse.SC_OK;

    } catch (BizException exception) {
      json = "{\"error\":\"" + exception.getMessage() + "\"}";

    } catch (FatalException exception) {
      json = "{\"error\":\"Problème interne\"}";
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    ServletUtils.sendResponse(resp, json, status);
  }

  /**
   * The objective of this method is to register a user.
   * 
   * @param req : the request to the server
   * @param resp : the response of the server
   * @param body : the map containing all the information of the user
   * @return the JSON containing an eventual error message
   * @throws Exception if something went wrong.
   */
  private void registerUser(HttpServletResponse resp, Map<String, Object> body) throws Exception {
    UserDto dto = dtoFactory.getUserDto();

    dto.setNom((String) body.get("nom"));
    dto.setPrenom((String) body.get("prenom"));
    dto.setPseudo((String) body.get("pseudo"));
    dto.setEmail((String) body.get("email"));
    dto.setVille((String) body.get("ville"));
    dto.setPassword((String) body.get("mdp"));

    String json = "{\"msg\":\"Wouhou! Connexion acceptée et en attente de confirmation.\"}";
    int status = HttpServletResponse.SC_OK;
    try {
      userUcc.inscrire(dto);
    } catch (FatalException fatality) {
      json = "{\"error\":\"Problème interne.\"}";
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    } catch (BizException biz) {
      json = "{\"error\":\"" + biz.getMessage() + ".\"}";
      status = HttpServletResponse.SC_CONFLICT;
    }
    ServletUtils.sendResponse(resp, json, status);
  }
}

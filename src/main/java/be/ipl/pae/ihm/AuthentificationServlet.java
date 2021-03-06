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
    String err = "{\"error\":\"Champ nom invalide\"}";
    String nom = body.get("nom").toString();
    if (nom.trim().isEmpty()) {
      ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    err = "{\"error\":\"Champ prenom invalide\"}";
    String prenom = body.get("prenom").toString();
    if (prenom.trim().isEmpty()) {
      ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    err = "{\"error\":\"Champ pseudo invalide\"}";
    String pseudo = body.get("pseudo").toString();
    if (pseudo.trim().isEmpty()) {
      ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    err = "{\"error\":\"Champ email invalide\"}";
    String email = body.get("email").toString();
    if (!email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,4})+$")) {
      ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    err = "{\"error\":\"Champ ville invalide\"}";
    String ville = body.get("ville").toString();
    if (ville.trim().isEmpty()) {
      ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    err = "{\"error\":\"Champ password mot de passe invalide\"}";
    String mdp = body.get("mdp").toString();
    if (mdp.trim().isEmpty()) {
      ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    UserDto dto = dtoFactory.getUserDto();

    dto.setNom(nom);
    dto.setPrenom(prenom);
    dto.setPseudo(pseudo);
    dto.setEmail(email);
    dto.setVille(ville);
    dto.setPassword(mdp);

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

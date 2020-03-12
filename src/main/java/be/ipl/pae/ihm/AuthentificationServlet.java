package be.ipl.pae.ihm;

import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.owlike.genson.Genson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthentificationServlet extends HttpServlet {


  private static final long serialVersionUID = 1L;
  private UserUcc userUcc;
  // private DtoFactory dtoFactory;
  private Genson genson;
  private String secret;

  /**
   * Instancie les attributs du servlet.
   * 
   * @param secret cle secrete
   * @param userUcc use case controller utilisateur
   *
   */

  public AuthentificationServlet(String secret, UserUcc userUcc/* , DtoFactory dtoFactory */) {
    this.secret = secret;
    this.userUcc = userUcc;
    // this.dtoFactory = dtoFactory;
    this.genson = new Genson();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      login(req, resp);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Gere la requete permettant de connecter un utilisateur
   * 
   * @param req
   * @param resp
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  private void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    StringBuffer jb = new StringBuffer();
    String line = null;

    try {
      BufferedReader reader = req.getReader();
      while ((line = reader.readLine()) != null) {
        jb.append(line);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    Map<String, Object> body = this.genson.deserialize(jb.toString(), Map.class);
    String pseudo = body.get("pseudo").toString();
    String password = body.get("password").toString();

    UserDto user = userUcc.seConnecter(pseudo, password);

    String json = null;
    if (user != null) {
      user.setPassword(null);
      String token = JWT.create().withClaim("id", user.getUserId()).sign(Algorithm.HMAC512(secret));
      json = "{\"token\":\"" + token + "\",\"user\":" + genson.serialize(user) + "}";
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      json = "{\"error\":\"Connexion failed\"}";
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.getWriter().write(json);
  }
}

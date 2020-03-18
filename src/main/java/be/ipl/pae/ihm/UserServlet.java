package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.main.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {


  private static final long serialVersionUID = 1L;
  @Inject
  private UserUcc userUcc;

  private Genson genson;
  private String secret;

  /**
   * Instancie un servlet.
   */
  public UserServlet() {
    super();
    this.genson = ServletUtils.getGensonUser();
    this.secret = Config.getConfiguration("secret");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      renvoyerListeUser(req, resp);

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Gére la requete permettant de lister tous les utilisateurs inscrit.
   * 
   * @param req : la requete
   * @param resp : le resp
   * @throws Exception : une exception
   */
  private void renvoyerListeUser(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String token = req.getHeader("Authorization");
    String json = null;
    int status = -1;

    if (ServletUtils.estConnecte(token)) {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      int userId = jwt.getClaim("id").asInt();
      UserDto userConnecte = userUcc.obtenirUser(userId);
      if (userConnecte.isOuvrier()) {
        if (req.getParameter("action").equals("listeUser")) {
          List<UserDto> listeUser = userUcc.listerUsers();
          json = "{\"listeUser\":" + genson.serialize(listeUser) + "}";
          status = HttpServletResponse.SC_OK;
        }

        if (req.getParameter("action").equals("confirmerInscription")) {
          List<UserDto> listeUsersPreinscrit = userUcc.listerUsersPreinscrit();
          json = "{\"success\": \"true\", \"data\":" + genson.serialize(listeUsersPreinscrit) + "}";
          status = HttpServletResponse.SC_OK;
        }

      } else {
        json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
        status = HttpServletResponse.SC_UNAUTHORIZED;
      }

    } else {
      json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
      status = HttpServletResponse.SC_UNAUTHORIZED;
    }

    ServletUtils.sendResponse(resp, json, status);
  }
}

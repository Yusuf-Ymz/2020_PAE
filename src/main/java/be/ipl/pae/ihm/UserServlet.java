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
  private String secret;

  /**
   * Instancie un servlet.
   */
  public UserServlet() {
    super();
    this.genson = ServletUtils.getGenson();
    this.secret = Config.getConfiguration("secret");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      displayListUsers(req, resp);

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doPost(req, resp);
    String token = req.getHeader("Authorization");
    if (ServletUtils.estConnecte(token)) {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      int userId = jwt.getClaim("id").asInt();

      Genson genson = new Genson();
      Map<String, Object> map = genson.deserialize(req.getReader(), Map.class);
      String email = map.get("email").toString();
      System.out.println(map.toString());
      int id = Integer.parseInt((String) map.get("id"));
      String nom = map.get("nom").toString();
      String prenom = map.get("prenom").toString();
      String pseudo = map.get("pseudo").toString();
      String ville = map.get("ville").toString();
      String action = map.get("action").toString();
      System.out.println(action);
      if (action.equals("confirmerInscription/onlyUser")) {
        System.out.println("réussie");

      }
      if (action.equals("confirmerInscription/worker")) {
      }
      if (action.equals("confirmerInscription/lienClient")) {
      }

    }
  }

  /**
   * Gére la requete permettant de lister les tous les utilisateurs inscrits.
   * 
   * @param req : la requete
   * @param resp : le resp
   * @throws Exception : une exception
   */
  private void displayListUsers(HttpServletRequest req, HttpServletResponse resp)

      throws IOException {

    String token = req.getHeader("Authorization");
    String json = null;
    if (ServletUtils.estConnecte(token)) {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      int userId = jwt.getClaim("id").asInt();

      if (req.getParameter("action").equals("listeUser")) {
        List<UserDto> listeUser = userUcc.listerUsers(userId);
        json = "{\"listeUser\":" + genson.serialize(listeUser) + "}";
        ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_OK);
      }

      if (req.getParameter("action").equals("confirmerInscription")) {
        List<UserDto> listeUsersPreinscrit = userUcc.listerUsersPreinscrit(userId);
        json = "{\"success\": \"true\", \"data\":" + genson.serialize(listeUsersPreinscrit) + "}";
        resp.setStatus(HttpServletResponse.SC_OK);
      }

    } else {
      json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.getWriter().write(json);

  }

}

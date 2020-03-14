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

public class OuvrierServlet extends HttpServlet {


  private static final long serialVersionUID = 1L;
  @Inject
  private UserUcc userUcc;

  private Genson genson;
  private String secret;

  public OuvrierServlet() {
    super();
    this.genson = new Genson();
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


  private void renvoyerListeUser(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String token = req.getHeader("Authorization");
    String json;
    if (token != null) {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      boolean isConfirme = jwt.getClaim("confirme").asBoolean();
      if (isConfirme) {
        List<UserDto> listeUser = userUcc.ListerUsers();
        json = "{\"listeUser\":" + genson.serialize(listeUser) + "}";
        resp.setStatus(HttpServletResponse.SC_OK);
      } else {
        json = "{\"error\":\"Vous n'avez pas accés à ces informations\"}";
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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

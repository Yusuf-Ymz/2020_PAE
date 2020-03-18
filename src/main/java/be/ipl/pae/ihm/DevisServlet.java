package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.ucc.DevisUcc;
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

public class DevisServlet extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String secret;
  private Genson genson;
  @Inject
  private DevisUcc devisUcc;

  public DevisServlet() {
    this.secret = Config.getConfiguration("secret");
    this.genson = ServletUtils.getGenson();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      renvoyerTousLesDevis(req, resp);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void renvoyerTousLesDevis(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String token = req.getHeader("Authorization");
    String json = null;

    if (token != null) {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      int userId = jwt.getClaim("id").asInt();

      List<DevisDto> tousLesDevis = devisUcc.listerTousLesDevis(userId);
      if (tousLesDevis != null) {
        json = "{\"devis\":" + genson.serialize(tousLesDevis) + "}";
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

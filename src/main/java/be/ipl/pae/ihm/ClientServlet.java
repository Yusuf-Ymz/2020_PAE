package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
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

public class ClientServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = -1254800213292593724L;
  private String secret;
  private Genson genson;



  @Inject
  private ClientUcc clientUcc;

  public ClientServlet() {

    this.secret = Config.getConfiguration("secret");
    this.genson = new Genson();

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      this.listCustomer(req, resp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    super.doPost(req, resp);
  }



  private void listCustomer(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    String token = req.getHeader("Authorization");
    String json = null;

    if (token != null) {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      int userId = jwt.getClaim("id").asInt();

      List<ClientDto> listeClients = this.clientUcc.listerClients(userId);

      if (listeClients != null) {
        json = "{\"clients\":" + genson.serialize(listeClients) + "}";
        resp.setStatus(HttpServletResponse.SC_OK);
      } else {
        json = "{\"error\":\"Vous n'avez pas accés informations\"}";
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    } else {
      json = "{\"error\":\"Vous n'avez pas accés ces informations\"}";
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.getWriter().write(json);

  }
}


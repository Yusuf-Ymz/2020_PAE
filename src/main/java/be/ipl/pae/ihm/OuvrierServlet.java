package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.main.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.convert.ContextualFactory;
import com.owlike.genson.reflect.BeanProperty;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

  /**
   * Instancie un servlet.
   */
  public OuvrierServlet() {
    super();
    this.genson = new GensonBuilder().useDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
        .exclude("password").withContextualFactory(new DateFactory()).create();
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
    String json;
    if (token != null) {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      boolean isConfirme = jwt.getClaim("confirme").asBoolean();
      if (isConfirme) {
        List<UserDto> listeUser = userUcc.listerUsers();
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

  private static class DateFactory implements ContextualFactory<LocalDate> {

    @Override
    public Converter<LocalDate> create(BeanProperty property, Genson genson) {
      return new DateConverter();
    }

  }

  private static class DateConverter implements Converter<LocalDate> {

    @Override
    public LocalDate deserialize(ObjectReader reader, Context ctx) throws Exception {
      return null;
    }

    @Override
    public void serialize(LocalDate object, ObjectWriter writer, Context ctx) throws Exception {
      writer.writeString(object.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

    }


  }
}

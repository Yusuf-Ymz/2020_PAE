package be.ipl.pae.ihm;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.exception.FatalException;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;

class ServletUtils {

  private static Genson gensonUser =
      new GensonBuilder().withConverters(new UtilisateurConverter()).create();

  private static Genson gensonDevis =
      new GensonBuilder().withContextualFactory(new DateFactory()).create();

  private static Genson gensonClient =
      new GensonBuilder().withConverters(new ClientConverter()).create();

  private static String secret = Config.getConfiguration("secret");

  private static Genson gensonAmenagement = new Genson();

  public static Genson getGensonUser() {
    return gensonUser;
  }

  public static Genson getGensonDevis() {
    return gensonDevis;
  }

  public static Genson getGensonClient() {
    return gensonClient;
  }


  public static int estConnecte(String token) {
    if (token != null) {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      int userId = jwt.getClaim("id").asInt();
      return userId;
    }
    return -1;
  }

  public static void sendResponse(HttpServletResponse resp, String json, int statusCode) {
    try {
      resp.setStatus(statusCode);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.getWriter().write(json);
    } catch (IOException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }

  }

  public static Genson getGensonAmenagement() {
    return gensonAmenagement;
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



  private static class ClientConverter implements Converter<ClientDto> {

    @Override
    public ClientDto deserialize(ObjectReader reader, Context ctx) throws Exception {
      return null;
    }

    @Override
    public void serialize(ClientDto object, ObjectWriter writer, Context ctx) throws Exception {
      writer.beginObject();
      writer.writeNumber("idClient", object.getIdClient()).writeString("nom", object.getNom())
          .writeString("prenom", object.getPrenom()).writeString("email", object.getEmail())
          .writeString("rue", object.getRue()).writeString("numero", object.getNumero())
          .writeString("boite", object.getBoite()).writeString("ville", object.getVille())
          .writeString("codePostal", object.getCodePostal())
          .writeString("telephone", object.getTelephone());
      writer.endObject();

    }


  }



  private static class UtilisateurConverter implements Converter<UserDto> {

    @Override
    public UserDto deserialize(ObjectReader reader, Context ctx) throws Exception {
      return null;
    }

    @Override
    public void serialize(UserDto object, ObjectWriter writer, Context ctx) throws Exception {
      writer.beginObject();
      writer.writeNumber("idUser", object.getUserId()).writeString("nom", object.getNom())
          .writeString("prenom", object.getPrenom()).writeString("pseudo", object.getPseudo())
          .writeString("email", object.getEmail()).writeString("ville", object.getVille())
          .writeString("dataInscription",
              object.getDateInscription().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

      writer.endObject();

    }


  }


}

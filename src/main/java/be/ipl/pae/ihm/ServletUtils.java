package be.ipl.pae.ihm;

import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.main.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.convert.ContextualFactory;
import com.owlike.genson.reflect.BeanProperty;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

class ServletUtils {

  private static Genson gensonUser =
      new GensonBuilder().withConverters(new UtilisateurConverter()).create();

  private static Genson gensonDevis =
      new GensonBuilder().withConverters(new DevisConverter()).create();

  private static Genson gensonClient =
      new GensonBuilder().withConverters(new ClientConverter()).create();

  private static Genson gensonAmenagement =
      new GensonBuilder().withConverters(new AmenagementConverter()).create();

  private static String secret = Config.getConfiguration("secret");

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
      writer.writeNumber("N° client", object.getIdClient()).writeString("Nom", object.getNom())
          .writeString("Prénom", object.getPrenom()).writeString("Email", object.getEmail())
          .writeString("Rue", object.getRue()).writeString("N° porte", object.getNumero())
          .writeString("Boite", object.getBoite()).writeString("Ville", object.getVille())
          .writeString("Code postal", object.getCodePostal())
          .writeString("Téléphone", object.getTelephone());
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
      writer.writeNumber("N° utilisateur", object.getUserId()).writeString("Nom", object.getNom())
          .writeString("Prénom", object.getPrenom()).writeString("Pseudo", object.getPseudo())
          .writeString("E-mail", object.getEmail()).writeString("Ville", object.getVille())
          .writeString("Date d'inscription",
              object.getDateInscription().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

      writer.endObject();

    }


  }

  private static class DevisConverter implements Converter<DevisDto> {
    @Override
    public DevisDto deserialize(ObjectReader reader, Context ctx) throws Exception {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void serialize(DevisDto object, ObjectWriter writer, Context ctx) throws Exception {
      // TODO Auto-generated method stub
      writer.beginObject();
      writer
          .writeString("Client", object.getClient().getNom() + " " + object.getClient().getPrenom())
          .writeString("Types d'aménagements",
              gensonAmenagement.serialize(object.getAmenagements(),
                  new GenericType<List<AmenagementDto>>() {}))
          .writeString("Date de début",
              object.getDateDebut().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
          .writeString("Date devis",
              object.getDateDevis().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
          .writeNumber("devisId", object.getDevisId()).writeNumber("duree", object.getDuree())
          .writeString("État d'avancement", object.getEtat())
          .writeNumber("Montant total", object.getMontantTotal())
          .writeString("Photo préférée", object.getPhotoPreferee());
      writer.endObject();
    }
  }

  private static class AmenagementConverter implements Converter<AmenagementDto> {
    @Override
    public AmenagementDto deserialize(ObjectReader reader, Context ctx) throws Exception {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void serialize(AmenagementDto object, ObjectWriter writer, Context ctx)
        throws Exception {
      // TODO Auto-generated method stub
      writer.beginObject();
      writer.writeString("libelle", object.getLibelle());
      writer.endObject();

    }
  }


}

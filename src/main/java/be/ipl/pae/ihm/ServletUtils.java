package be.ipl.pae.ihm;

import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

  private static Genson gensonPhoto =
      new GensonBuilder().withConverters(new PhotoConverter()).create();

  private static Genson gensonUtilisateurSansMdp = new GensonBuilder().exclude("password").create();

  private static String secret = Config.getConfiguration("secret");


  /**
   * Renvoie un genson pour l'utilisateur.
   * 
   * @return un genson
   */
  public static Genson getGensonUser() {
    return gensonUser;
  }

  /**
   * Renvoie un genson pour devis
   * 
   * @return un genson
   */
  public static Genson getGensonDevis() {
    return gensonDevis;
  }

  /**
   * Renvoie un genson pour le client.
   * 
   * @return un genson
   */
  public static Genson getGensonClient() {
    return gensonClient;
  }

  public static String serializeUser(UserDto user) {
    return gensonUtilisateurSansMdp.serialize(user);
  }

  /**
   * Vérifie si l'utilisateur est connecté(token) ou pas.
   * 
   * @param token : le token à verifier
   * @return l'id de utilisateur ou -1 si pas de token valide
   */
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

  /**
   * Renvoie la reponse au front-end.
   * 
   * @param resp : la reponse de la requete
   * @param json : le body de la reponse
   * @param statusCode : le status code
   */
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

  /**
   * Traite les données envoyés au server et les mets dans une map.
   * 
   * @param req : la requete
   * @return une map avec les données de la requete
   */
  public static Map<String, Object> decoderBodyJson(HttpServletRequest req) {
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

    Map<String, Object> body = gensonUser.deserialize(jb.toString(), Map.class);
    return body;
  }

  /**
   * Renvoie un genson.
   * 
   * @return le genson d'amenagement
   */
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
          .writeString("Ville", object.getVille())
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

      if (object.getClient() != null) {
        writer.writeString("Nom du client", object.getClient().getNom())
            .writeString("Prénom du client", object.getClient().getPrenom());
      }

      writer
          .writeString("Types d'aménagements",
              gensonAmenagement.serialize(object.getAmenagements(),
                  new GenericType<List<AmenagementDto>>() {}))
          .writeString("Date de début",
              object.getDateDebut().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
          .writeString("Date devis",
              object.getDateDevis().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
          .writeNumber("devisId", object.getDevisId()).writeNumber("duree", object.getDuree())
          .writeString("État d'avancement", object.getEtat())
          .writeNumber("Montant total", object.getMontantTotal());

      if (object.getPhotoPreferee() != null) {
        writer.writeString("Photo préférée", object.getPhotoPreferee().getPhoto());
      }

      if (object.getPhotosAvant() != null) {
        writer.writeString("Photos Avant",
            gensonPhoto.serialize(object.getPhotosAvant(), new GenericType<List<PhotoDto>>() {}));
        writer.writeString("Photos Apres",
            gensonPhoto.serialize(object.getPhotosApres(), new GenericType<List<PhotoDto>>() {}));
      }

      writer.endObject();
    }
  }

  private static class PhotoConverter implements Converter<PhotoDto> {
    @Override
    public PhotoDto deserialize(ObjectReader reader, Context ctx) throws Exception {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void serialize(PhotoDto object, ObjectWriter writer, Context ctx) throws Exception {
      // TODO Auto-generated method stub
      writer.beginObject();

      writer.writeNumber("Photo id", object.getPhotoId());
      writer.writeString("Photo", object.getPhoto());

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

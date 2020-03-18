package be.ipl.pae.ihm;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.exception.FatalException;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;

class ServletUtils {

  private static Genson genson = new GensonBuilder().withConverters(new UserConverter()).create();

  public static Genson getGenson() {
    return genson;
  }


  public static boolean estConnecte(String token) {
    if (token == null)
      return false;
    return true;
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

  static class UserConverter implements Converter<UserDto> {

    @Override
    public void serialize(UserDto user, ObjectWriter writer, Context ctx) throws Exception {
      System.out.println("enter");
      writer.beginObject();
      writer.writeString("pseudo", user.getPseudo()).writeString("nom", user.getNom())
          .writeString("prenom", user.getPrenom()).writeString("email", user.getEmail())
          .writeString("ville", user.getVille()).writeString("date_inscription",
              user.getDateInscription().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
      writer.endObject();
    }

    public UserDto deserialize(ObjectReader reader, Context ctx) throws Exception {
      return null;
    };
  }

  static class DevisConverter implements Converter<DevisDto> {

    @Override
    public void serialize(DevisDto devis, ObjectWriter writer, Context ctx) throws Exception {
      writer.beginObject();
      writer.writeNumber("devisId", devis.getDevisId())
          .writeString("photoPreferee", devis.getPhotoPreferee())
          .writeNumber("clientId", devis.getClient())
          .writeString("dateDebut",
              devis.getDateDebut().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
          .writeNumber("montantTotal", devis.getMontantTotal())
          .writeNumber("duree", devis.getDuree()).writeString("etat", devis.getEtat());
      writer.endObject();
    }

    @Override
    public DevisDto deserialize(ObjectReader reader, Context ctx) throws Exception {
      return null;
    }

  }
}

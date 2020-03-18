package be.ipl.pae.bizz.bizz;

import be.ipl.pae.bizz.dto.UserDto;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import org.eclipse.jetty.server.Authentication.User;

import java.time.format.DateTimeFormatter;

public class UserConverter implements Converter<UserDto> {
  @Override
  public void serialize(UserDto user, ObjectWriter writer, Context ctx) throws Exception {
    // TODO Auto-generated method stub
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

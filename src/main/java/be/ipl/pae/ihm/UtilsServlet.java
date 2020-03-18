package be.ipl.pae.ihm;

import be.ipl.pae.exception.FatalException;

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

class UtilsServlet {

  private static Genson genson =
      new GensonBuilder().exclude("password").withContextualFactory(new DateFactory()).create();

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
    public void serialize(LocalDate date, ObjectWriter writer, Context ctx) throws Exception {
      writer.writeString(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

    }


  }
}

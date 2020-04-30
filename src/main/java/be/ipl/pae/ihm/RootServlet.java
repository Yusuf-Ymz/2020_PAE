package be.ipl.pae.ihm;

import org.eclipse.jetty.servlet.DefaultServlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RootServlet extends DefaultServlet {


  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      if (req.getRequestURI().contentEquals("/")) {

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        String body = readHtmlContent();
        resp.getWriter().write(body);

      } else {
        super.doGet(req, resp);
      }

    } catch (Exception exception) {
      exception.printStackTrace();
      resp.setStatus(500);
      resp.setContentType("text/html");
      byte[] msgBytes = exception.getMessage().getBytes("UTF-8");
      resp.setContentLength(msgBytes.length);
      resp.setCharacterEncoding("utf-8");
      resp.getOutputStream().write(msgBytes);
    }
  }


  /**
   * Lit tous les fichiers html present dans le repetoire public/views.
   * 
   * @return le body de html
   * @throws IOException : l'exception lancée
   */
  private String readHtmlContent() throws IOException {

    String basePathDirectories = "./public/views/";

    StringBuilder body = new StringBuilder();


    body.append(new String(Files.readAllBytes(Paths.get(basePathDirectories + "index.html"))));
    body.append(new String(Files.readAllBytes(Paths.get(basePathDirectories + "loader.html"))));

    body.append(new String(Files.readAllBytes(Paths.get(basePathDirectories + "header.html"))));
    body.append(new String(Files.readAllBytes(Paths.get(basePathDirectories + "carousel.html"))));
    body.append(new String(Files.readAllBytes(Paths.get(basePathDirectories + "login.html"))));
    body.append(
        new String(Files.readAllBytes(Paths.get(basePathDirectories + "inscription.html"))));
    body.append(new String(Files.readAllBytes(Paths.get(basePathDirectories + "searchBar.html"))));
    body.append(new String(
        Files.readAllBytes(Paths.get(basePathDirectories + "confirmedInscription.html"))));
    body.append(
        new String(Files.readAllBytes(Paths.get(basePathDirectories + "linkUserClient.html"))));
    body.append(
        new String(Files.readAllBytes(Paths.get(basePathDirectories + "introduireDevis.html"))));
    body.append(
        new String(Files.readAllBytes(Paths.get(basePathDirectories + "consulterDevis.html"))));

    return body.toString();
  }

}

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
    System.out.println("test");
    String basePathDirectories = "./public/views/";

    String body = new String(Files.readAllBytes(Paths.get(basePathDirectories + "index.html")));

    body += new String(Files.readAllBytes(Paths.get(basePathDirectories + "loader.html")));
    body += new String(Files.readAllBytes(Paths.get(basePathDirectories + "header.html")));
    body += new String(Files.readAllBytes(Paths.get(basePathDirectories + "carousel.html")));
    body += new String(Files.readAllBytes(Paths.get(basePathDirectories + "login.html")));
    body += new String(Files.readAllBytes(Paths.get(basePathDirectories + "inscription.html")));
    body += new String(Files.readAllBytes(Paths.get(basePathDirectories + "searchBar.html")));
    body += new String(
        Files.readAllBytes(Paths.get(basePathDirectories + "confirmedInscription.html")));
    body += new String(Files.readAllBytes(Paths.get(basePathDirectories + "linkUserClient.html")));
    body += new String(Files.readAllBytes(Paths.get(basePathDirectories + "introduireDevis.html")));
    body += new String(Files.readAllBytes(Paths.get(basePathDirectories + "consulterDevis.html")));

    return body;
  }

}

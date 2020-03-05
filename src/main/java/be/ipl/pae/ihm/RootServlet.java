package be.ipl.pae.ihm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class RootServlet extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      if (req.getRequestURI().contentEquals("/")) {
        System.out.println("AUTH:" + req.getRequestURI());
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        String body = new String(Files.readAllBytes(Paths.get("./public/index.html")));
        resp.getWriter().write(body);
      } else {
        try {
          Path path = Paths.get("public" + req.getRequestURI());
          File file = new File(path.toString());
          String mimeType = "";
          if (path.toString().contains(".js")) {
            mimeType = "application/javascript";
          } else if (path.toString().contains(".html")) {
            mimeType = "text/html";
          } else if (path.toString().contains(".css")) {
            mimeType = "text/css";
          } else if (path.toString().contains(".jpeg")) {
            mimeType = "image/jpeg";
          } else if (path.toString().contains(".png")) {
            mimeType = "image/png";
          }

          System.out.println("FILENAME:" + file.getName() + "URI:" + req.getRequestURI()
              + " Content type:" + mimeType);
          resp.setContentType(mimeType);
          resp.setCharacterEncoding("utf-8");
          resp.setStatus(HttpServletResponse.SC_OK);

          String fileContent = new String(Files.readAllBytes(path));
          resp.getWriter().write(fileContent);
        } catch (IOException err) {
          resp.getWriter().write("This ressource does not exist");
        }
      }
    } catch (Exception err) {
      err.printStackTrace();
      resp.setStatus(500);
      resp.setContentType("text/html");
      byte[] msgBytes = err.getMessage().getBytes("UTF-8");
      resp.setContentLength(msgBytes.length);
      resp.setCharacterEncoding("utf-8");
      resp.getOutputStream().write(msgBytes);
    }
  }

}

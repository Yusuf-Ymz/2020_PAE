package be.ipl.pae.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import javax.servlet.http.HttpServlet;

public class Main {
  /**
   * Couche IHM : instancie le serveur et les servlets.
   * 
   * @param args tableau de String
   * @throws Exception est une RuntimeException
   */
  public static void main(String[] args) throws Exception {
    WebAppContext context = new WebAppContext();

    System.out.println(context.getContextPath());
    context.setContextPath("/");

    HttpServlet rootServlet = new RootServlet();
    context.addServlet(new ServletHolder(rootServlet), "/");
    context.setResourceBase("public");

    Server server = new Server(8080);
    server.setHandler(context);
    server.start();
  }

}

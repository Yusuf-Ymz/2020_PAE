package be.ipl.pae.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

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

    context.addServlet(new ServletHolder(new DefaultServlet()), "/");
    context.setResourceBase("public");

    Server server = new Server(8080);
    server.setHandler(context);
    server.start();
  }

}

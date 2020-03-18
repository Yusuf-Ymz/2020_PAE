package be.ipl.pae.main;

import be.ipl.pae.ihm.AuthentificationServlet;
import be.ipl.pae.ihm.OuvrierServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
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
    context.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");
    context.addServlet(new ServletHolder(new DefaultServlet()), "/");
    context.setResourceBase("public");

    Config.load("prod.properties");
    InjectionService injectionService = new InjectionService();

    HttpServlet authentificationServlet = new AuthentificationServlet();
    injectionService.injectDependencies(authentificationServlet);
    context.addServlet(new ServletHolder(authentificationServlet), "/authentification");
    HttpServlet ouvrierServlet = new OuvrierServlet();
    injectionService.injectDependencies(ouvrierServlet);
    context.addServlet(new ServletHolder(ouvrierServlet), "/ouvrier");

    Server server = new Server(Config.getConfigurationToInt("port"));
    server.setHandler(context);
    server.start();
  }

}

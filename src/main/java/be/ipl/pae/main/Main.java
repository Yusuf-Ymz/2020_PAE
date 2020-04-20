package be.ipl.pae.main;

import be.ipl.pae.ihm.AmenagementServlet;
import be.ipl.pae.ihm.AuthentificationServlet;
import be.ipl.pae.ihm.ClientServlet;
import be.ipl.pae.ihm.DevisServlet;
import be.ipl.pae.ihm.PhotoServlet;
import be.ipl.pae.ihm.RootServlet;
import be.ipl.pae.ihm.UserServlet;

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
    context.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");

    context.addServlet(new ServletHolder(new RootServlet()), "/");
    context.setResourceBase("public");

    Config.load("prod.properties");
    InjectionService injectionService = new InjectionService();

    HttpServlet authentificationServlet = new AuthentificationServlet();
    injectionService.injectDependencies(authentificationServlet);
    context.addServlet(new ServletHolder(authentificationServlet), "/authentification");

    HttpServlet userServlet = new UserServlet();
    injectionService.injectDependencies(userServlet);
    context.addServlet(new ServletHolder(userServlet), "/user");

    HttpServlet devisServlet = new DevisServlet();
    injectionService.injectDependencies(devisServlet);
    context.addServlet(new ServletHolder(devisServlet), "/devis");

    HttpServlet clientServlet = new ClientServlet();
    injectionService.injectDependencies(clientServlet);
    context.addServlet(new ServletHolder(clientServlet), "/client");

    HttpServlet amenagementServlet = new AmenagementServlet();
    injectionService.injectDependencies(amenagementServlet);
    context.addServlet(new ServletHolder(amenagementServlet), "/amenagement");

    HttpServlet photoServlet = new PhotoServlet();
    injectionService.injectDependencies(photoServlet);
    context.addServlet(new ServletHolder(photoServlet), "/photo");

    Server server = new Server(Config.getConfigurationToInt("port"));
    server.setHandler(context);
    server.start();
  }

}

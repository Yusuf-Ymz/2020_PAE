package be.ipl.pae.main;

import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.ihm.AuthentificationServlet;
import be.ipl.pae.persistance.dal.DalService;
import be.ipl.pae.persistance.dao.UserDao;

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

    context.addServlet(new ServletHolder(new DefaultServlet()), "/");
    context.setResourceBase("public");

    InjectionService inject = new InjectionService("prod.properties");
    DalService dalService = inject.getDependency(DalService.class, inject.getConfiguration("url"),
        inject.getConfiguration("user"), inject.getConfiguration("password"));
    DtoFactory dtoFactory = inject.getDependency(DtoFactory.class);
    UserDao userDao = inject.getDependency(UserDao.class, dalService, dtoFactory);
    UserUcc userUcc = inject.getDependency(UserUcc.class, userDao);

    HttpServlet authentificationServlet =
        new AuthentificationServlet(inject.getConfiguration("JwtSecret"), userUcc, dtoFactory);
    context.addServlet(new ServletHolder(authentificationServlet), "/authentification");

    Server server = new Server(Integer.parseInt(inject.getConfiguration("port")));
    server.setHandler(context);
    server.start();
  }

}

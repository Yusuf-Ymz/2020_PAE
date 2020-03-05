package be.ipl.pae.main;

import be.ipl.pae.bizz.bizz.DtoFactory;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.persistance.dal.DalService;
import be.ipl.pae.persistance.dao.UserDao;
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

    DalService dalService = InjectionService.getDependency(DalService.class);
    DtoFactory dtoFactory = InjectionService.getDependency(DtoFactory.class);
    UserDao userDao = InjectionService.getDependency(UserDao.class, dalService, dtoFactory);
    UserUcc userUcc = InjectionService.getDependency(UserUcc.class, userDao);

    WebAppContext context = new WebAppContext();

    System.out.println(context.getContextPath());
    context.setContextPath("/");

    context.addServlet(new ServletHolder(new DefaultServlet()), "/");
    context.setResourceBase("public");

    Server server = new Server(Integer.parseInt(InjectionService.getConfiguration("port")));
    server.setHandler(context);
    server.start();
  }

}

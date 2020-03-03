package be.ipl.pae.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.http.HttpServlet;

public class Main {

  public static void main(String[] args) throws Exception {
    Server server = new Server(80);
    WebAppContext context = new WebAppContext();

    System.out.println(context.getContextPath());
    context.setContextPath("/");

    HttpServlet rootServlet = new RootServlet();
    context.addServlet(new ServletHolder(rootServlet), "/");
    context.setResourceBase("public");

    server.setHandler(context);
    server.start();
  }

}

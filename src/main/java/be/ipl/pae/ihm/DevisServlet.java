package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.main.Config;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DevisServlet extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String secret;
  private Genson genson;
  @Inject
  private DevisUcc devisUcc;

  public DevisServlet() {
    this.secret = Config.getConfiguration("secret");
    this.genson = ServletUtils.getGensonDevis();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      String token = req.getHeader("Authorization");
      int userId = ServletUtils.estConnecte(token);
      String json = "{\"error\":\"Vous n'avez pas accès à ces informations\"}";
      int status = HttpServletResponse.SC_UNAUTHORIZED;
      if (userId != -1) {
        String action = req.getParameter("action");
        List<DevisDto> devis = null;

        switch (action) {
          case "mesDevis":
            devis = devisUcc.listerSesDevis(userId);
            break;
          case "tousLesDevis":
            devis = devisUcc.listerTousLesDevis(userId);
            break;
        }

        if (devis == null) {
          ServletUtils.sendResponse(resp, json, status);
        } else {
          json = "{\"devis\":" + genson.serialize(devis) + "}";
          status = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, status);
        }
      } else {
        ServletUtils.sendResponse(resp, json, status);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}

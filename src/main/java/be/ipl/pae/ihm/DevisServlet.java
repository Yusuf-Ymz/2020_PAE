package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.exception.FatalException;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DevisServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Genson gensonTousLesDevis;
  private Genson gensonClientDevis;
  @Inject
  private DevisUcc devisUcc;

  /**
   * Instancie le devis servlet.
   */
  public DevisServlet() {
    this.gensonTousLesDevis = ServletUtils.getGensonTousLesDevis();
    this.gensonClientDevis = ServletUtils.getGensonClientDevis();
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
        List<DevisDto> listeDevis = null;
        DevisDto devis = null;
        String objetSerialize = null;
        switch (action) {
          case "mesDevis":
            listeDevis = devisUcc.listerSesDevis(userId);
            objetSerialize =
                gensonClientDevis.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
            break;
          case "tousLesDevis":
            listeDevis = devisUcc.listerTousLesDevis(userId);
            objetSerialize =
                gensonTousLesDevis.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
            break;
          case "devisDuClient":

            int clientId = Integer.parseInt(req.getParameter("N° client").toString());

            listeDevis = devisUcc.listerDevisClient(userId, clientId);
            objetSerialize =
                gensonTousLesDevis.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
            break;
          case "consulterDevis":
            int devisId = Integer.parseInt(req.getParameter("devisId").toString());
            devis = devisUcc.consulterDevis(userId, devisId);
            objetSerialize = gensonTousLesDevis.serialize(devis, new GenericType<DevisDto>() {});
            break;
          default:
            break;
        }

        if (listeDevis != null) {
          json = "{\"devis\":" + objetSerialize + "}";
        } else {
          json = "{\"devis\":" + objetSerialize + "}";
        }
        status = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, status);
      } else {
        ServletUtils.sendResponse(resp, json, status);
      }
    } catch (BizException exception) {
      exception.printStackTrace();
      int status = HttpServletResponse.SC_FORBIDDEN;
      String json = "{\"error\":\"" + exception.getMessage() + "\"";
      ServletUtils.sendResponse(resp, json, status);
    } catch (FatalException exception) {
      exception.printStackTrace();
      String json = "{\"error\":\"" + exception.getMessage() + "\"";
      int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      ServletUtils.sendResponse(resp, json, status);
    }
  }
}

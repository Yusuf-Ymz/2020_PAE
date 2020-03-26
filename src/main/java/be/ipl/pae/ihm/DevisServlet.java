package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.exception.FatalException;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DevisServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Genson genson;
  @Inject
  private DtoFactory fact;
  @Inject
  private DevisUcc devisUcc;

  /**
   * Instancie le devis servlet.
   */
  public DevisServlet() {
    this.genson = ServletUtils.getGensonDevis();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
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
            objetSerialize = genson.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
            break;
          case "tousLesDevis":
            listeDevis = devisUcc.listerTousLesDevis(userId);
            objetSerialize = genson.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
            break;
          case "devisDuClient":

            int clientId = Integer.parseInt(req.getParameter("N° client").toString());

            listeDevis = devisUcc.listerDevisClient(userId, clientId);
            objetSerialize = genson.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
            break;
          case "consulterDevis":
            int devisId = Integer.parseInt(req.getParameter("devisId").toString());
            devis = devisUcc.consulterDevis(userId, devisId);
            objetSerialize = genson.serialize(devis, new GenericType<DevisDto>() {});
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

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // super.doPost(req, resp);
    // TODO il faut tester si c'est un ouvrier
    StringBuffer jb = new StringBuffer();
    String line = null;

    try {
      BufferedReader reader = req.getReader();
      while ((line = reader.readLine()) != null) {
        jb.append(line);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    Map<String, Object> body = this.genson.deserialize(jb.toString(), Map.class);
    String action = body.get("action").toString();
    switch (action) {
      case "insererDevis":
        insererDevis(body, resp);
        break;
      default:
        super.doPost(req, resp);
        break;
    }
  }

  private void insererDevis(Map<String, Object> body, HttpServletResponse resp) {
    try {
      int idClient = Integer.parseInt((String) body.get("idClient"));
      LocalDate dateDebut = LocalDate.parse(body.get("dateDebut").toString());
      int montantTotal = Integer.parseInt(body.get("montant").toString());
      int nbJours = Integer.parseInt(body.get("nbJours").toString());
      String amenagements = body.get("amenagements").toString();
      String photos = body.get("photos").toString();
      int lAmenagements[] = genson.deserialize(amenagements, int[].class);
      photos = photos.replace("[", "");
      photos = photos.replace("]", "");
      String[] lphotos = photos.split(",");
      for (int i = 0; i < lphotos.length; i++) {
        lphotos[i] = lphotos[i].replace("?????", ",");
      }

      DevisDto devis = fact.getDevisDto();
      devis.setDateDebut(dateDebut);
      devis.setDuree(nbJours);
      devis.setMontantTotal(montantTotal);
      DevisDto newDevis = devisUcc.insererDevis(devis, idClient, lAmenagements, lphotos);
      String json =
          "{\"devis\":" + genson.serialize(newDevis, new GenericType<DevisDto>() {}) + "}";
      int statusCode = HttpServletResponse.SC_OK;
      ServletUtils.sendResponse(resp, json, statusCode);
    } catch (BizException exception) {
      String err = "{\"error\":" + exception.getMessage() + "\"}";
      int statusCode = HttpServletResponse.SC_CONFLICT;
      ServletUtils.sendResponse(resp, err, statusCode);
    } catch (FatalException exception) {
      String err = "{\"error\":\" Erreur serveur \"}";
      int statusCode = HttpServletResponse.SC_CONFLICT;
      ServletUtils.sendResponse(resp, err, statusCode);
    } catch (Exception exception) {
      exception.printStackTrace();
    }



  }
}

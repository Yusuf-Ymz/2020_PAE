package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.AmenagementUcc;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.exception.FatalException;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AmenagementServlet extends HttpServlet {

  @Inject
  private DtoFactory dtoFactory;
  @Inject
  private AmenagementUcc amenagementUcc;
  @Inject
  private UserUcc userUcc;
  private Genson genson;
  private static final long serialVersionUID = 1L;

  public AmenagementServlet() {
    this.genson = new Genson();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    List<AmenagementDto> amenagements = amenagementUcc.listerTousLesAmenagements();
    String json = "{\"amenagements\":" + genson.serialize(amenagements) + "}";
    ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_OK);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String token = req.getHeader("Authorization");
    int idUser = ServletUtils.estConnecte(token);
    String json = "{\"error\":\"Vous n'avez pas accès à ces informations\"}";
    int statusCode = HttpServletResponse.SC_UNAUTHORIZED;

    if (idUser != -1 && this.userUcc.obtenirUtilisateur(idUser).isOuvrier()) {
      Map<String, Object> body = ServletUtils.decoderBodyJson(req);
      String libelle = body.get("libelle").toString();
      if (libelle.trim().equals("")) {
        json = "{\"error\":\"Champ libelle incorrect\"}";
        statusCode = HttpServletResponse.SC_BAD_REQUEST;
        ServletUtils.sendResponse(resp, json, statusCode);
        return;
      }
      AmenagementDto amenagement = dtoFactory.getAmenagementDto();
      amenagement.setLibelle(libelle);
      try {
        AmenagementDto newAmenagement = amenagementUcc.ajouterAmenagement(amenagement);
        json = "{\"amenagement\":" + genson.serialize(newAmenagement) + "}";
        statusCode = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, statusCode);
      } catch (FatalException exception) {
        json = "{\"error\":\"Erreur serveur\"}";
        statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        ServletUtils.sendResponse(resp, json, statusCode);
      }
    } else {
      ServletUtils.sendResponse(resp, json, statusCode);
    }
  }

}

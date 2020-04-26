package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.bizz.ucc.PhotoUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.exception.FatalException;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PhotoServlet extends HttpServlet {

  @Inject
  private PhotoUcc photoUcc;

  @Inject
  private DevisUcc devisUcc;

  private Genson genson;
  private static final long serialVersionUID = 1L;

  public PhotoServlet() {
    this.genson = ServletUtils.getGensonPhoto();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {

      int status = HttpServletResponse.SC_OK;
      String action = req.getParameter("action");
      if (action.equals("afficherPhotoCarrousel")) {
        listerPhotoCarrousel(resp, status);
      } else if (action.equals("afficherPhotoAmenagement")) {
        listerPhotoParAmenagement(req, resp);
      } else {
        super.doGet(req, resp);
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

  private void listerPhotoParAmenagement(HttpServletRequest req, HttpServletResponse resp) {
    String json = "\"error\":\"Erreur champ aménagement\"";
    try {
      int idAmenagement = Integer.parseInt(req.getParameter("amenagement").toString());

      List<PhotoDto> photos = photoUcc.listerPhotoParAmenagement(idAmenagement);
      json = "{\"photosCarrousel\":"
          + genson.serialize(photos, new GenericType<List<PhotoDto>>() {}) + "}";
      int status = HttpServletResponse.SC_OK;
      ServletUtils.sendResponse(resp, json, status);
    } catch (FatalException exception) {
      String error = "{\"error\":\"" + exception.getMessage() + "\"}";
      int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      exception.printStackTrace();
      ServletUtils.sendResponse(resp, error, status);
    } catch (Exception exception) {
      int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      ServletUtils.sendResponse(resp, json, status);
      exception.printStackTrace();
    }



  }



  private void listerPhotoCarrousel(HttpServletResponse resp, int status) {
    List<PhotoDto> listePhotos;
    try {
      listePhotos = photoUcc.listerPhotoCarrousel();
      String objetSerialize = genson.serialize(listePhotos, new GenericType<List<PhotoDto>>() {});
      String json = "{\"photosCarrousel\":" + objetSerialize + "}";
      ServletUtils.sendResponse(resp, json, status);
    } catch (Exception exception) {
      exception.printStackTrace();
      String json = "{\"error\":\" Erreur serveur \"}";
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      ServletUtils.sendResponse(resp, json, status);
    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      Map<String, Object> body = ServletUtils.decoderBodyJson(req);
      String action = body.get("action").toString();

      switch (action) {
        case "ajouterPhotoApresAmenagement":
          ajouterPhotoApresApresAmenagement(body, resp);
          break;
        default:
          super.doPost(req, resp);
          break;
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

  private void ajouterPhotoApresApresAmenagement(Map<String, Object> body,
      HttpServletResponse resp) {
    String photo = body.get("image").toString();
    int idAmenagement = Integer.parseInt(body.get("typeAmenagement").toString());
    int idDevis = Integer.parseInt(body.get("idDevis").toString());
    boolean visible = Boolean.parseBoolean(body.get("photoVisible").toString());
    boolean preferee = Boolean.parseBoolean(body.get("photoPreferee").toString());
    String json = "{\"error\":\"Erreur du serveur\"";
    try {
      final PhotoDto newPhoto =
          devisUcc.insererPhotoApresAmenagement(photo, idAmenagement, idDevis, visible, preferee);
      json = "{\"success\":\"Photo ajouté\",\"photo\":"
          + genson.serialize(newPhoto, new GenericType<PhotoDto>() {}) + "}";
      ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_OK);
    } catch (BizException exception) {
      json = "{\"error\":\"" + exception.getMessage() + "\"}";
      ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_CONFLICT);
    } catch (FatalException exception) {
      json = "{\"error\":\"Erreur du serveur\"}";
      ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (Exception exception) {
      exception.printStackTrace();
      ServletUtils.sendResponse(resp, json, HttpServletResponse.SC_PRECONDITION_FAILED);
    }
  }
}

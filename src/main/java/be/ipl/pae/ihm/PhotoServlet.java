package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.PhotoUcc;
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

public class PhotoServlet extends HttpServlet {
  @Inject
  private DtoFactory dtoFactory;
  @Inject
  private PhotoUcc photoUcc;
  @Inject
  private PhotoUcc userUcc;
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
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {

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

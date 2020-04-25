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
      String token = req.getHeader("Authorization");
      String json = "{\"error\":\"Vous n'avez pas accès à ces informations\"}";
      int status = HttpServletResponse.SC_OK;
      String action = req.getParameter("action");
      if (action.equals("afficherPhotoCarrousel")) {
        listerPhotoCarrousel(resp, status);
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
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void listerPhotoCarrousel(HttpServletResponse resp, int status) throws Exception {
    List<PhotoDto> listePhotos = photoUcc.listerPhotoCarrousel();
    String objetSerialize = genson.serialize(listePhotos, new GenericType<List<PhotoDto>>() {});
    String json = "{\"photosCarrousel\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
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

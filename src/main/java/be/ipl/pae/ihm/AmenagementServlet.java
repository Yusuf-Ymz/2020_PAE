package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.AmenagementDto;
import be.ipl.pae.bizz.ucc.AmenagementUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AmenagementServlet extends HttpServlet {
  // @Inject
  // private DtoFactory dtoFactory;
  @Inject
  private AmenagementUcc amenagementUcc;
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
    super.doPost(req, resp);
  }

}

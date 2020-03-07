package be.ipl.pae.ihm;

import be.ipl.pae.bizz.bizz.DtoFactory;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.ucc.UserUcc;
import com.owlike.genson.Genson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthentificationServlet extends HttpServlet {

  private UserUcc userUcc;
  private DtoFactory dtoFactory;
  private Genson genson;
  private String secret;

  /**
   * Instancie les attributs du servlet.
   * 
   * @param secret cle secrete
   * @param userUcc use case controller utilisateur
   * @param dtoFactory factory data transfer object
   */
  public AuthentificationServlet(String secret, UserUcc userUcc, DtoFactory dtoFactory) {
    this.secret = secret;
    this.userUcc = userUcc;
    this.dtoFactory = dtoFactory;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    super.doPost(req, resp);
  }

  private UserDto login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    String pseudo = req.getParameter("pseudo");
    String password = req.getParameter("password");
    UserDto user = dtoFactory.getUserDto();
    UserDto userDto = userUcc.seConnecter(user);
    userDto.setPassword(null);
    return userDto;
  }
}

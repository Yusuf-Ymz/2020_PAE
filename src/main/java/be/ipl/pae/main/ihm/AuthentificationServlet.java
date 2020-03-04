package be.ipl.pae.main.ihm;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import be.ipl.pae.main.biz.dto.UserDto;
import be.ipl.pae.main.biz.factory.DtoFactory;
import be.ipl.pae.main.biz.ucc.UserUcc;

public class AuthentificationServlet extends HttpServlet {

  private UserUcc userUcc;
  private DtoFactory dtoFactory;
  private Genson genson;
  private String secret;

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
    UserDto userDto = userUcc.seConnecter(pseudo, password);
    if (Objects.nonNull(userDto)) {

    }
    userDto.setPassword(null);
    return userDto;
  }
}

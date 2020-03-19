package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.main.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.owlike.genson.Genson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthentificationServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	@Inject
	private UserUcc userUcc;
	@Inject
	private DtoFactory dtoFactory;
	private Genson genson;
	private String secret;



	/**
	 * Instancie un servlet.
	 */
	public AuthentificationServlet() {
		this.secret = Config.getConfiguration("secret");
		this.genson = new Genson();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

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
		String action = (String)body.get("action");
		String json = "";
		
		switch(action) {
		case "register" :
			try {
				json = registerUser(req, resp, body);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "connection" :
			try {
				json = login(req, resp, body);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * Gére la requete permettant de connecter un utilisateur.
	 * 
	 * @param req : la requete
	 * @param resp : le resp
	 * @throws Exception : une exception
	 */
	@SuppressWarnings("unchecked")
	private String login(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> body) throws Exception {
		
		String pseudo = body.get("pseudo").toString();
		String password = body.get("password").toString();

		UserDto user = userUcc.seConnecter(pseudo, password);

		String json = null;
		if (user != null) {
			user.setPassword(null);
			String token = JWT.create().withClaim("id", user.getUserId()).sign(Algorithm.HMAC512(secret));
			json = "{\"token\":\"" + token + "\",\"user\":" + genson.serialize(user) + "}";
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		} else {
			json = "{\"error\":\"La connexion a échoué. Pseudo et mot de passe non correspondants.\"}";
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		}
		return json;
	}
	
	/**
	 * The objective of this method is to register a user.
	 * @param req : the request to the server
	 * @param resp : the response of the server
	 * @param body : the map containing all the information of the user
	 * @return the JSON containing an eventual error message
	 * @throws Exception if something went wrong.
	 */
	private String registerUser(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> body) throws Exception {
		UserDto dto = dtoFactory.getUserDto();
		
		dto.setNom((String)body.get("nom"));
		dto.setPrenom((String)body.get("prenom"));
		dto.setPseudo((String)body.get("pseudo"));
		dto.setEmail((String)body.get("email"));
		dto.setVille((String)body.get("ville"));
		dto.setPassword((String)body.get("mdp"));

		String token = JWT.create().withClaim("id", dto.getUserId()).sign(Algorithm.HMAC512(secret));
		String json = "{\"success\":\"true\",\"msg\":\"Wouhou! Connexion acceptée et en attente de confirmation.\"}";
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(json);
		
		return json;
	}
}
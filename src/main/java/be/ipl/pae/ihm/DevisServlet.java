package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.dto.UserDto;
import be.ipl.pae.bizz.factory.DtoFactory;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.bizz.ucc.UserUcc;
import be.ipl.pae.exception.BizException;
import be.ipl.pae.exception.FatalException;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

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
  @Inject
  private UserUcc userUcc;

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
      String action = req.getParameter("action");
      List<DevisDto> listeDevis = null;
      DevisDto devis = null;
      String objetSerialize = null;

      if (userId != -1) {

        UserDto user = this.userUcc.obtenirUtilisateur(userId);

        if (user.isConfirme() && !user.isOuvrier()) {

          switch (action) {
            case "mesDevis":

              listeDevis = devisUcc.listerDevisDUnCLient(user.getClientId());
              objetSerialize = genson.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
              break;
            case "consulterDevisEnTantQueClient":
              int devisId = Integer.parseInt(req.getParameter("devisId").toString());
              devis = devisUcc.consulterDevisEnTantQueUtilisateur(user.getClientId(), devisId);
              objetSerialize = genson.serialize(devis, new GenericType<DevisDto>() {});
              break;
            default:
              break;
          }
        } else if (user.isOuvrier()) {

          switch (action) {

            case "tousLesDevis":
              listeDevis = devisUcc.listerTousLesDevis();
              objetSerialize = genson.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
              break;
            case "devisDuClient":
              int clientId = Integer.parseInt(req.getParameter("N° client").toString());

              listeDevis = devisUcc.listerDevisDUnCLient(clientId);
              objetSerialize = genson.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
              break;
            case "consulterDevisEnTantQueOuvrier":
              int devisId = Integer.parseInt(req.getParameter("devisId").toString());
              devis = devisUcc.consulterDevisEnTantQueOuvrier(devisId);
              objetSerialize = genson.serialize(devis, new GenericType<DevisDto>() {});
              break;
            default:
              super.doGet(req, resp);
              break;
          }

        } else {
          ServletUtils.sendResponse(resp, json, status);
        }


        json = "{\"devis\":" + objetSerialize + "}";


      } else {
        ServletUtils.sendResponse(resp, json, status);
      }



      status = HttpServletResponse.SC_OK;
      ServletUtils.sendResponse(resp, json, status);
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
    Map<String, Object> body = ServletUtils.decoderBodyJson(req);

    String action = body.get("action").toString();

    switch (action) {
      case "insererDevis":
        insererDevis(body, resp);
        break;

      case "confirmerCommande":
        changerEtat(body, resp, action);
        break;

      case "confirmerDateDebut":
        changerEtat(body, resp, action);
        break;

      case "repousserDateDebut":
        break;

      case "ajouterPhotoApresAmenagement":
        ajouterPhotoApresApresAmenagement(body, resp);
        break;
      default:
        super.doPost(req, resp);
        break;
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

  private void insererDevis(Map<String, Object> body, HttpServletResponse resp) {
    String err = "";
    try {
      err = "Veuillez renseigner un client";
      final int idClient = Integer.parseInt((String) body.get("idClient"));
      err = "Veuillez saisir une date";
      final LocalDate dateDebut = LocalDate.parse(body.get("dateDebut").toString());
      err = "Champ \"montant total\" incorrect";
      final int montantTotal = Integer.parseInt(body.get("montant").toString());
      err = "Champ \"durée des travaux\" incorrect";
      final int nbJours = Integer.parseInt(body.get("nbJours").toString());
      final String amenagements = body.get("amenagements").toString();
      if (amenagements.equals("[]")) {
        err = "Veuillez renseigner des aménagements";
        ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_PRECONDITION_FAILED);
        return;
      }
      String photos = body.get("photos").toString();
      err = "Veuillez sélectionner des aménagements";

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
      int[] lesAmenagements = genson.deserialize(amenagements, int[].class);
      DevisDto newDevis = devisUcc.insererDevis(devis, idClient, lesAmenagements, lphotos);

      String json =
          "{\"devis\":" + genson.serialize(newDevis, new GenericType<DevisDto>() {}) + "}";
      int statusCode = HttpServletResponse.SC_OK;
      ServletUtils.sendResponse(resp, json, statusCode);
    } catch (BizException exception) {
      err = "{\"error\":\\\"" + exception.getMessage() + "\"}";
      int statusCode = HttpServletResponse.SC_CONFLICT;
      ServletUtils.sendResponse(resp, err, statusCode);
    } catch (FatalException exception) {
      err = "{\"error\":\" Erreur serveur \"}";
      int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      ServletUtils.sendResponse(resp, err, statusCode);
    } catch (Exception exception) {
      exception.printStackTrace();
      ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_PRECONDITION_FAILED);
    }
  }

  private void changerEtat(Map<String, Object> body, HttpServletResponse resp, String nouvelEtat) {

    int devisId = Integer.parseInt(body.get("id").toString());
    String json;
    int status;
    try {
      switch (nouvelEtat) {
        case "confirmerCommande":
          devisUcc.changerEtatDevis(devisId, "Commande confirmée");
          json = "{\"etat\":\"Commande confirmée\"}";
          status = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, status);
          break;

        case "confirmerDateDebut":
          devisUcc.changerEtatDevis(devisId, "Date début confirmée");
          json = "{\"etat\":\"Date début confirmée\"}";
          status = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, status);
          break;

        case "repousserDateDebut":
          json = "{\"etat\":\"A changer.\"}";
          status = HttpServletResponse.SC_OK;
          ServletUtils.sendResponse(resp, json, status);
          break;
        default:
          break;
      }
    } catch (BizException exception) {
      exception.printStackTrace();
      json = "{\"error\":\"État invalide\"}";
      status = HttpServletResponse.SC_NOT_ACCEPTABLE;
      ServletUtils.sendResponse(resp, json, status);
    } catch (FatalException exception) {
      exception.printStackTrace();
      json = "{\"error\":\"interne\"}";
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      ServletUtils.sendResponse(resp, json, status);
    }
  }
}

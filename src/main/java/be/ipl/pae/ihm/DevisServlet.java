package be.ipl.pae.ihm;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.dto.DevisDto;
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
      int status = HttpServletResponse.SC_OK;

      String action = req.getParameter("action");

      if (userId != -1) {

        UserDto user = this.userUcc.obtenirUtilisateur(userId);

        if (user.isConfirme() && !user.isOuvrier()) {
          switch (action) {
            case "mesDevis":
              listerMesDevis(resp, user.getClientId(), status);
              break;
            case "consulterDevisEnTantQueClient":
              int devisId = Integer.parseInt(req.getParameter("devisId").toString());
              consulterDevisEnTantQueClient(resp, devisId, user.getClientId(), status);
              break;
            case "getTypes":
              listerAmenagementsRecherches(resp, req.getParameter("keyword"), user.getClientId(),
                  status);
              break;
            case "listerMesDevisAffine":
              listerDevisAffine(resp, req, user.getClientId(), status);
              break;
            default:
              break;
          }
        } else {

          switch (action) {

            case "tousLesDevis":
              listerTousLesDevis(resp, status);
              break;
            case "devisDuClient":
              int clientId = Integer.parseInt(req.getParameter("N° client").toString());
              listerLesDevisDUnClient(resp, clientId, status);
              break;
            case "consulterDevisEnTantQueOuvrier":
              int devisId = Integer.parseInt(req.getParameter("devisId").toString());
              consulterDevisEnTantQueOuvrier(resp, devisId, status);
              break;
            case "getNom":
              listerNomsClientsRecherches(resp, req.getParameter("keyword"), status);
              break;
            case "getPrenom":
              listerPrenomsClientsRecherches(resp, req.getParameter("keyword"), status);
              break;
            case "getTypes":
              listerAmenagementsTousLesClientsRecherches(resp, req.getParameter("keyword"), status);
              break;
            case "listerTousLesDevisAffine":
              listerDevisAffine(resp, req, -1, status);
              break;
            default:
              super.doGet(req, resp);
              break;
          }

        }
      } else {
        status = HttpServletResponse.SC_UNAUTHORIZED;
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

  /**
   * Gère la requête permettant d'afficher les devis d'un client.
   * 
   * @param resp : la réponse à envoyer au client
   * @param clientId : l'id du client dont on doit afficher les devis
   * @param status : status ok
   */
  private void listerMesDevis(HttpServletResponse resp, int clientId, int status) {
    List<DevisDto> listeDevis = devisUcc.listerDevisDUnCLient(clientId);
    String objetSerialize = genson.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
    String json = "{\"devis\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  /**
   * Gère la requête permettant d'afficher un de ses devis en tant qu'utilisateur.
   * 
   * @param resp : la réponse à envoyer au client
   * @param devisId : l'id du devis à afficher
   * @param clientId : l'id du client à qui appartient le devis
   * @param status : status ok
   */
  private void consulterDevisEnTantQueClient(HttpServletResponse resp, int devisId, int clientId,
      int status) {
    DevisDto devis = devisUcc.consulterDevisEnTantQueUtilisateur(clientId, devisId);
    String objetSerialize = genson.serialize(devis, new GenericType<DevisDto>() {});
    String json = "{\"devis\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  /**
   * Gère la requête permettant d'afficher le devis d'un utilisateur.
   * 
   * @param resp : la réponse à envoyer au client
   * @param devisId : l'id du devis à afficher
   * @param status : status ok
   */
  private void consulterDevisEnTantQueOuvrier(HttpServletResponse resp, int devisId, int status) {
    DevisDto devis = devisUcc.consulterDevisEnTantQueOuvrier(devisId);
    String objetSerialize = genson.serialize(devis, new GenericType<DevisDto>() {});
    String json = "{\"devis\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  /**
   * Gère la requête permettant d'afficher tous les devis.
   * 
   * @param resp : la réponse à envoyer au client
   * @param status : status ok
   */
  private void listerTousLesDevis(HttpServletResponse resp, int status) {
    List<DevisDto> listeDevis = devisUcc.listerTousLesDevis();
    String objetSerialize = genson.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
    String json = "{\"devis\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  /**
   * Gère la requête permettant d'afficher tous les devis d'un client.
   * 
   * @param resp : la réponse à envoyer au client
   * @param clientId : le client en question
   * @param status : status ok
   */
  private void listerLesDevisDUnClient(HttpServletResponse resp, int clientId, int status) {
    List<DevisDto> listeDevis = devisUcc.listerDevisDUnCLient(clientId);
    String objetSerialize = genson.serialize(listeDevis, new GenericType<List<DevisDto>>() {});
    String json = "{\"devis\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  /**
   * Gère la requête permettant d'afficher tous les devis d'un client.
   * 
   * @param resp : la réponse à envoyer au client
   * @param keyword : caractères à match
   * @param clientId : le client en question
   * @param status : status ok
   */
  private void listerAmenagementsRecherches(HttpServletResponse resp, String keyword, int clientId,
      int status) {
    List<String> amenagements = devisUcc.listerAmenagementsRecherches(keyword, clientId);
    String objetSerialize = genson.serialize(amenagements);
    String json = "{\"noms\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  /**
   * Gère la requête permettant d'afficher dans la barre des recherches les noms de clients
   * correspondant au keyword.
   * 
   * @param resp : la réponse à envoyer au client
   * @param keyword : caractères à matcher
   * @param status : status ok
   */
  private void listerNomsClientsRecherches(HttpServletResponse resp, String keyword, int status) {
    List<String> noms = devisUcc.listerNomsClients(keyword);
    String objetSerialize = genson.serialize(noms);
    String json = "{\"noms\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  private void listerPrenomsClientsRecherches(HttpServletResponse resp, String keyword,
      int status) {
    List<String> prenoms = devisUcc.listerPrenomsClients(keyword);
    String objetSerialize = genson.serialize(prenoms);
    String json = "{\"prenoms\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  /**
   * Gère la requête permettant d'afficher dans la barre des recherches les types d'aménagements
   * correspondant au keyword.
   * 
   * @param resp : la réponse à envoyer au client
   * @param keyword : caractères à matcher
   * @param status : status ok
   */
  private void listerAmenagementsTousLesClientsRecherches(HttpServletResponse resp, String keyword,
      int status) {
    List<String> amenagements = devisUcc.listerAmenagementsTousLesClients(keyword);
    String objetSerialize = genson.serialize(amenagements);
    String json = "{\"amenagements\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  /**
   * Gère la requête permettant de lister tous les devis ou les devis d'un seul client selon des
   * critères.
   * 
   * @param resp : la réponse à envoyer au client
   * @param req : la requête
   * @param clientId : l'id du client(si tous les devis -1)
   * @param status : status ok
   */
  private void listerDevisAffine(HttpServletResponse resp, HttpServletRequest req, int clientId,
      int status) {
    final String nomClient = req.getParameter("nomClient");
    final String prenomClient = req.getParameter("prenomClient");
    final String typeAmenagement = req.getParameter("type");
    final String dateDevis = req.getParameter("date");

    int montantMin = Integer.MIN_VALUE;
    if (!req.getParameter("montantMin").trim().isEmpty()) {
      montantMin = Integer.parseInt(req.getParameter("montantMin"));
    }

    int montantMax = Integer.MAX_VALUE;
    if (!req.getParameter("montantMax").trim().isEmpty()) {
      montantMax = Integer.parseInt(req.getParameter("montantMax"));
    }

    List<DevisDto> devis = null;

    if (clientId != -1) {
      devis = devisUcc.listerMesDevisAffine(clientId, typeAmenagement, dateDevis, montantMin,
          montantMax);
    } else {
      devis = devisUcc.listerTousLesDevisAffine(nomClient, prenomClient, typeAmenagement, dateDevis,
          montantMin, montantMax);
    }

    String objetSerialize = genson.serialize(devis, new GenericType<List<DevisDto>>() {});
    String json = "{\"devis\":" + objetSerialize + "}";
    ServletUtils.sendResponse(resp, json, status);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // super.doPost(req, resp);
    // TODO il faut tester si c'est un ouvrier
    Map<String, Object> body = ServletUtils.decoderBodyJson(req);

    String action = body.get("action").toString();
    String json;
    int status;
    int devisId = Integer.parseInt(body.get("id").toString());

    switch (action) {
      case "insererDevis":
        insererDevis(body, resp);
        break;

      case "confirmerCommande":
        LocalDate dateDebut = LocalDate.parse(body.get("date").toString());
        devisUcc.confirmerCommandeAmenagement(devisId, dateDebut);
        json = "{\"etat\":\"Commande confirmée\"}";
        status = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, status);
        break;

      case "confirmerDateDebut":
        devisUcc.changerEtatDevis(devisId, "Acompte payé");
        json = "{\"etat\":\"Acompte payé\"}";
        status = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, status);
        break;

      case "repousserDateDebut":
        String strDate = body.get("newDate").toString();
        LocalDate date = LocalDate.parse(strDate);
        devisUcc.repousserDate(devisId, date);
        json = "{\"etat\":\"Acompte payé\" ,\"date\":\"" + body.get("newDate").toString() + "\"}";
        status = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, status);
        break;

      case "indiquerFactureMilieuEnvoyee":
        System.out.println("je passe");
        devisUcc.changerEtatDevis(devisId, "Facture de milieu chantier envoyée");
        json = "{\"etat\":\"Facture milieu chantier envoyée\"}";
        status = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, status);
        break;

      case "indiquerFactureFinEnvoyee":
        devisUcc.changerEtatDevis(devisId, "Facture de fin de chantier envoyée");
        json = "{\"etat\":\"Facture de fin de chantier envoyée\"}";
        status = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, status);
        break;

      case "rendreVisible":
        devisUcc.changerEtatDevis(devisId, "Visible");
        json = "{\"etat\":\"visible\"}";
        status = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, status);
        break;

      case "annulerDemande":
        devisUcc.changerEtatDevis(devisId, "Annulé");
        json = "{\"etat\":\"Annulé\"}";
        status = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, status);
        break;
      case "supprimerDateDebut":
        devisUcc.supprimerDateDebutTravaux(devisId);
        json = "{\"etat\":\"Absence paiement de l'acompte\"}";
        status = HttpServletResponse.SC_OK;
        ServletUtils.sendResponse(resp, json, status);
        break;
      default:
        super.doPost(req, resp);
        break;

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
      devis.setDateDevis(dateDebut);
      devis.setDuree(nbJours);
      devis.setMontantTotal(montantTotal);
      int[] lesAmenagements = genson.deserialize(amenagements, int[].class);
      DevisDto newDevis = devisUcc.insererDevis(devis, idClient, lesAmenagements, lphotos);

      String json =
          "{\"devis\":" + genson.serialize(newDevis, new GenericType<DevisDto>() {}) + "}";
      int statusCode = HttpServletResponse.SC_OK;
      ServletUtils.sendResponse(resp, json, statusCode);
    } catch (BizException exception) {
      exception.printStackTrace();
      err = "{\"error\":\\\"" + exception.getMessage() + "\"}";
      int statusCode = HttpServletResponse.SC_CONFLICT;
      ServletUtils.sendResponse(resp, err, statusCode);
    } catch (FatalException exception) {
      exception.printStackTrace();
      err = "{\"error\":\" Erreur serveur \"}";
      int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      ServletUtils.sendResponse(resp, err, statusCode);
    } catch (Exception exception) {
      exception.printStackTrace();
      ServletUtils.sendResponse(resp, err, HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}

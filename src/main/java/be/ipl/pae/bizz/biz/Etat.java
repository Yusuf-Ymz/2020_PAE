package be.ipl.pae.bizz.biz;

enum Etat {

  INTRODUIT("Devis introduit"), ANNULE("Annulé"), COMMANDE_CONFIRMEE(
      "Commande confirmée"), ACOMPTE_PAYE("Acompte payé"), FACTURE_MILIEU_CHANTIER(
          "Facture de milieu chantier envoyée"), VISIBLE("Visible"), FACTURE_FIN_CHANTIER(
              "Facture de fin de chantier envoyée"), ABSENCE_PAYEMENT_ACOMPTE(
                  "Absence paiement de l'acompte"), NO_ETAT("");

  private String etat;

  Etat(String etat) {
    this.etat = etat;
  }

  public String getEtat() {
    return etat;
  }
}

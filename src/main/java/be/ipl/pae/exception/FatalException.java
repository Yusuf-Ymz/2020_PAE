package be.ipl.pae.exception;

public class FatalException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructeur par défaut permettant de créer une exception.
   */
  public FatalException() {
    super();
  }


  /**
   * Constructeur permettant de créer une exception en passant en paramètre un message.
   * 
   * @param message : message à afficher
   */
  public FatalException(String message) {
    super(message);
  }
}

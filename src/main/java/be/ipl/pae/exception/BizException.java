package be.ipl.pae.exception;


public class BizException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructeur par défaut permettant de créer une exception (Qui sera appelée dans la couche
   * biz).
   */
  public BizException() {
    super();
  }

  /**
   * Constructeur permettant de créer une exception en passant en paramètre un message.
   * 
   * @param message : message à afficher
   */
  public BizException(String message) {
    super(message);
  }



}

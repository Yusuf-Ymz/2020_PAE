package be.ipl.pae.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDb {
  /**
   * Correspond au champ de la BD.
   * 
   * @return la valeur du champ correspondant dans la Db
   */
  String value() default "";

}

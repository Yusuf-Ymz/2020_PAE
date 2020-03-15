package be.ipl.pae.persistance.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public interface DalService {



  /**
   * Crée un PreparedStatement relatif à la requête SQL et le renvoie.
   * 
   * @param statement Le querry à exécuter
   * @return le PreparedStatement créé
   */
  PreparedStatement createStatement(String statement);

  /**
   * Rempli les attributs par introspection de l'objet passé en paramètre à partir des données en DB
   * (le ResultSet).
   * 
   * @param obj : l'objet
   * @param rs : le ResultSet
   */
  void fillObject(Object obj, Map<String, Object> dbObjects);

  Map<String, Object> convertResulSetToMap(ResultSet rs);
}

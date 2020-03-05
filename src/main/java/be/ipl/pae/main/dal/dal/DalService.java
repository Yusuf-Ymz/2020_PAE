package be.ipl.pae.main.dal.dal;




public interface DalService {

  String GET_USER = "SELECT * FROM pae.utilisateurs u WHERE u.pseudo  = ? ;";


}

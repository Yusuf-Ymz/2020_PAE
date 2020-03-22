package be.ipl.pae.persistance.dal;

public interface DalServices {

  void startTransaction();

  void commitTransaction();

  void rollbackTransaction();

}

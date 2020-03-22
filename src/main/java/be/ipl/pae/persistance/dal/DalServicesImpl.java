package be.ipl.pae.persistance.dal;

import be.ipl.pae.exception.FatalException;
import be.ipl.pae.main.Config;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;


class DalServicesImpl implements DalServices, DalBackendServices {
  private static ThreadLocal<Connection> connection = new ThreadLocal<Connection>();;
  private DataSource ds = setupDataSource();

  /**
   * Crée une connexion à la DB.
   * 
   */
  public DalServicesImpl() {

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException exception) {
      System.out.println("Driver PostgreSQL manquant !");
      exception.printStackTrace();
      System.exit(1);
    }
    DalServicesImpl.connection = new ThreadLocal<Connection>();
    this.ds = setupDataSource();
  }


  public PreparedStatement createStatement(String query) {
    PreparedStatement stmt = null;
    Connection conn = connection.get();
    try {
      stmt = conn.prepareStatement(query);
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException();
    }
    return stmt;
  }


  public void startTransaction() {
    try {
      Connection conn = ds.getConnection();
      connection.set(conn);
      conn.setAutoCommit(false);
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  public void commitTransaction() {
    try {
      Connection conn = connection.get();
      connection.remove();
      conn.commit();
      conn.close();
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  public void rollbackTransaction() {
    try {
      Connection conn = connection.get();
      conn.rollback();
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  private DataSource setupDataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName("org.postgresql.Driver");
    ds.setUrl(Config.getConfiguration("url"));
    ds.setUsername(Config.getConfiguration("user"));
    ds.setPassword(Config.getConfiguration("password"));
    return ds;
  }
}

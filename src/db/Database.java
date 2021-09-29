package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Database {
  private static Database instance;

  public static Database getInstance() {
    if (instance == null) {
      instance = new Database();
    }

    return instance;
  }

  private SessionFactory sessionFactory;

  private Database() {
    try {
      setupHibernate();
    } catch (Exception error) {
      System.out.println("Não foi possível conectar-se ao banco de dados.");
    }
  }

  public Session openSession() {
    Session session = sessionFactory.openSession();
    return session;
  }

  protected void setupHibernate() throws Exception {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
        .configure() // configures settings from hibernate.cfg.xml
        .build();
    try {
      sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }
    catch (Exception e) {
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      System.out.println("Não foi possível conectar-se ao banco de dados, registro destruído.");
      System.out.println(e.getMessage());
      
      e.printStackTrace();
      StandardServiceRegistryBuilder.destroy( registry );
    }
  }
}

package hu.nl.hibernate;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.boot.Metadata;  
import org.hibernate.boot.MetadataSources;  
import org.hibernate.boot.registry.StandardServiceRegistry;  
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Main {
  private static SessionFactory factory;
  public static void main(String[] args) throws SQLException, ParseException {
      try {
        factory = new Configuration().configure()
//                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();
      } catch (Throwable ex) {
        System.err.println("Failed to create sessionFactory object." + ex);
        throw new ExceptionInInitializerError(ex);
      }
      Session session = factory.openSession();
      Transaction t = session.beginTransaction();

      Log log = new Log(1,"Hibernate works!");
      session.save(log);

      /* Extra POJO interaction */
      ManageEmployee manageEmployees = new ManageEmployee(factory);

      int employeeID = manageEmployees.addEmployee("Tristan", "ter Haar", 4000);
      manageEmployees.addEmployee("Test", "Achternaam", 5678);

      manageEmployees.listEmployees();

      System.out.println("Deleting first employee...");
      manageEmployees.deleteEmployee(employeeID);

      manageEmployees.listEmployees();

      t.commit();  
      System.out.println("successfully saved");    
      factory.close();  
      session.close();   
  }
}

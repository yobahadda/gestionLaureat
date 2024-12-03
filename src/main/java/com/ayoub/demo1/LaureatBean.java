package com.ayoub.demo1;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.ayoub.demo1.util.HibernateUtil;

import java.util.List;

@ManagedBean(name = "laureatBean")
@SessionScoped
public class LaureatBean {
    @PersistenceContext
    private EntityManager entityManager;
    private Laureat laureat = new Laureat();
    private List<Laureat> laureats;
    public List<Laureat> getLaureats() {
        if (laureats == null) {
            laureats = entityManager.createQuery("SELECT l FROM Laureat l", Laureat.class)
                    .getResultList();
        }
        return laureats;
    }
    public Laureat getLaureat() {
        return laureat;
    }

    public void setLaureat(Laureat laureat) {
        this.laureat = laureat;
    }

    // Method to save the Laureat data to the database
    public String saveLaureat() {
        // Open Hibernate session
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            // Save Laureat entity to the database
            session.save(laureat);
            transaction.commit();  // Commit transaction
            return "success";  // Navigate to a success page (or back to the form)
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback if error occurs
            }
            e.printStackTrace();
            return "failure";  // Error handling
        } finally {
            session.close();  // Close the session
        }
    }
}

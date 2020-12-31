package org.epita.game2.infrastructure;

import org.epita.game2.domaine.Partie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PartieDaoImpl implements PartieDao {

    public void create(Partie partie) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(partie);
        session.getTransaction().commit();
        session.close();
    }

    public Partie getPartieByNom(String nom) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("SELECT p FROM Partie p WHERE p.nom=:nom");
        q.setParameter("nom",nom);
        List<Partie> parties = q.getResultList();
        session.close();
        if (parties != null && parties.size() != 0) {
            return parties.get(0);
        }
        return null;
    }

    public List<String> getNomsPartieByDebutNom(String nom) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("SELECT p.nom FROM Partie p WHERE p.nom LIKE :nomStartWith");
        q.setParameter("nomStartWith", nom+"%");
        List<String> nomParties = q.getResultList();
        session.close();
        return nomParties;
    }
}

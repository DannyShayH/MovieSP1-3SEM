package app.daos;

import app.entities.Point;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PointsDAO {

    private static EntityManagerFactory emf;


    public PointsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Point createPoint(Point p) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }
        return p;
    }

    public Long getPointCount() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Long> q1 = em.createQuery("SELECT COUNT(p) FROM Point p", Long.class);
            return q1.getSingleResult();
        }
    }

    public Double getAvgCount(){
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Double> q2 = em.createQuery("SELECT AVG(p.x) FROM Point p", Double.class);
            return q2.getSingleResult();
        }
    }

    public List<Point> allPoints() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
            query.setMaxResults(30);
            return query.getResultList();
        }
    }
}


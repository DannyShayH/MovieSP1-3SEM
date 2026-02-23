package app;

import app.config.HibernateConfig;
import app.daos.PointsDAO;
import app.entities.Point;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) {


        EntityManager em = emf.createEntityManager();
        PointsDAO pointsDAO = new PointsDAO(emf);

        for (int i = 0; i < 1000; i++) {
            Point p = Point.builder()
                    .x(i)
                    .y(i)
                    .build();
           pointsDAO.createPoint(p);
        }

        // Find the number of Point objects in the database:
//        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total Points: " + pointsDAO.getPointCount());

        // Find the average X value:
//        TypedQuery<Double> q2 = em.createQuery("SELECT AVG(p.x) FROM Point p", Double.class);
        System.out.println("Average X: " + pointsDAO.getAvgCount());

        // Retrieve all the Point objects from the database and print the first 30:
//        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
//        query.setMaxResults(30);
//        List<Point> results = query.getResultList();
//        results.forEach(System.out::println);
        pointsDAO.allPoints().forEach(System.out::println);

        // Close the database connection:

        em.close();
        emf.close();
    }
}
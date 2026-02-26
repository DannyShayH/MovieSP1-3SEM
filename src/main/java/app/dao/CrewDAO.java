package app.dao;

import app.entities.Crew;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;


public class CrewDAO implements IDAO<Crew> {
    private static EntityManagerFactory emf;

    public CrewDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Crew create(Crew crew) {
        try (EntityManager em = emf.createEntityManager()) {
            Crew crewInDatabase = em.find(Crew.class, crew.getCrewId());
            if (crewInDatabase != null) {
                System.out.println("Crew already exists with id: " + crew.getCrewId());
                return crew;
            }
            em.getTransaction().begin();
            em.persist(crew);
            em.getTransaction().commit();
            return crew;
        }
    }

    @Override
    public Crew getById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Crew crew = em.find(Crew.class, id);
            if (crew == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return crew;
        }
    }

    @Override
    public Crew update(Crew crew) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Crew updatedCrew = em.merge(crew);
            em.getTransaction().commit();
            return updatedCrew;
        }
    }

    @Override
    public Crew delete(long crewId) {
        try(EntityManager em = emf.createEntityManager()){
            Crew crew = em.find(Crew.class, crewId);
            if(crew == null)
                throw new EntityNotFoundException("No entity found with id: "+ crewId);
            em.getTransaction().begin();
            em.remove(crew);
            em.getTransaction().commit();
            return crew;
        }
    }

    @Override
    public Set<Crew> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Crew> query = em.createQuery("SELECT c FROM Crew c", Crew.class);
            return new HashSet<>(query.getResultList());
        }
    }
}

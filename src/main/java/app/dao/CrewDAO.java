package app.dao;

import app.entities.CrewInMovie;
import app.interfaces.IDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;


public class CrewDAO implements IDAO<CrewInMovie> {
    private static EntityManagerFactory emf;


    @Override
    public CrewInMovie create(CrewInMovie crewInMovie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(crewInMovie);
            em.getTransaction().commit();
            return crewInMovie;
        }
    }

    @Override
    public CrewInMovie getById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            CrewInMovie crewInMovie = em.find(CrewInMovie.class, id);
            if (crewInMovie == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return crewInMovie;
        }
    }

    @Override
    public CrewInMovie update(CrewInMovie crewInMovie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            CrewInMovie updatedCrewInMovie = em.merge(crewInMovie);
            em.getTransaction().commit();
            return updatedCrewInMovie;
        }
    }

    @Override
    public CrewInMovie delete(long crewId) {
        try(EntityManager em = emf.createEntityManager()){
            CrewInMovie crewInMovie = em.find(CrewInMovie.class, crewId);
            if(crewInMovie == null)
                throw new EntityNotFoundException("No entity found with id: "+ crewId);
            em.getTransaction().begin();
            em.remove(crewInMovie);
            em.getTransaction().commit();
            return crewInMovie;
        }
    }

    @Override
    public Set<CrewInMovie> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<CrewInMovie> query = em.createQuery("SELECT c FROM CrewInMovie c", CrewInMovie.class);
            return new HashSet<>(query.getResultList());
        }
    }
}

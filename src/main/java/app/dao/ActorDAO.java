package app.dao;

import app.entities.ActorInMovie;
import app.interfaces.IDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class ActorDAO implements IDAO<ActorInMovie> {
    private static EntityManagerFactory emf;


    @Override
    public ActorInMovie create(ActorInMovie actorInMovie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(actorInMovie);
            em.getTransaction().commit();
            return actorInMovie;
        }
    }

    @Override
    public ActorInMovie getById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            ActorInMovie actorInMovie = em.find(ActorInMovie.class, id);
            if (actorInMovie == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return actorInMovie;
        }
    }

    @Override
    public ActorInMovie update(ActorInMovie actorInMovie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            ActorInMovie updatedActorInMovie = em.merge(actorInMovie);
            em.getTransaction().commit();
            return updatedActorInMovie;
        }
    }

    @Override
    public ActorInMovie delete(long actorId) {
        try(EntityManager em = emf.createEntityManager()){
            ActorInMovie actorInMovie = em.find(ActorInMovie.class, actorId);
            if(actorInMovie == null)
                throw new EntityNotFoundException("No entity found with id: "+ actorId);
            em.getTransaction().begin();
            em.remove(actorInMovie);
            em.getTransaction().commit();
            return actorInMovie;
        }
    }

    @Override
    public Set<ActorInMovie> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<ActorInMovie> query = em.createQuery("SELECT a FROM ActorInMovie a", ActorInMovie.class);
            return new HashSet<>(query.getResultList());
        }
    }
}

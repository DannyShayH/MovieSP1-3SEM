package app.dao;

import app.dto.ActorDTO;
import app.entities.Actor;
import app.entities.Movie;
import app.persistence.IDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class ActorDAO implements IDAO<Actor> {
    private static EntityManagerFactory emf;


    @Override
    public Actor create(Actor actor) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(actor);
            em.getTransaction().commit();
            return actor;
        }
    }

    @Override
    public Actor getById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Actor actor = em.find(Actor.class, id);
            if (actor == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return actor;
        }
    }

    @Override
    public Actor update(Actor actor) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Actor updatedActor = em.merge(actor);
            em.getTransaction().commit();
            return updatedActor;
        }
    }

    @Override
    public Actor delete(long actorId) {
        try(EntityManager em = emf.createEntityManager()){
            Actor actor = em.find(Actor.class, actorId);
            if(actor == null)
                throw new EntityNotFoundException("No entity found with id: "+ actorId);
            em.getTransaction().begin();
            em.remove(actor);
            em.getTransaction().commit();
            return actor;
        }
    }

    @Override
    public Set<Actor> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Actor> query = em.createQuery("SELECT a FROM Actor a", Actor.class);
            return new HashSet<>(query.getResultList());
        }
    }
}

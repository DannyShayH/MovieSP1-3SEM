package app.dao;

import app.entities.Director;
import app.entities.Genre;
import app.persistence.IDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class DirectorDAO implements IDAO<Director> {

    private static EntityManagerFactory emf;

    @Override
    public Director create(Director director) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(director);
            em.getTransaction().commit();
            return director;
        }
    }

    @Override
    public Director getById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Director director = em.find(Director.class, id);
            if (director == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return director;
        }
    }

    @Override
    public Director update(Director director) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Director updatedDirector = em.merge(director);
            em.getTransaction().commit();
            return updatedDirector;
        }
    }

    @Override
    public Director delete(long directorId) {
        try(EntityManager em = emf.createEntityManager()){
            Director director = em.find(Director.class, directorId);
            if(director == null)
                throw new EntityNotFoundException("No entity found with id: "+ directorId);
            em.getTransaction().begin();
            em.remove(director);
            em.getTransaction().commit();
            return director;
        }
    }

    @Override
    public Set<Director> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Director> query = em.createQuery("SELECT d FROM Director d", Director.class);
            return new HashSet<>(query.getResultList());
        }
    }
}

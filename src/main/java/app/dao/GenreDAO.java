package app.dao;

import app.entities.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class GenreDAO implements IDAO<Genre> {
    private static EntityManagerFactory emf;


    @Override
    public Genre create(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(genre);
            em.getTransaction().commit();
            return genre;
        }
    }

    @Override
    public Genre getById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Genre genre = em.find(Genre.class, id);
            if (genre == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return genre;
        }
    }

    @Override
    public Genre update(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Genre updateGenre = em.merge(genre);
            em.getTransaction().commit();
            return updateGenre;
        }
    }

    @Override
    public Genre delete(long genreId) {
        try(EntityManager em = emf.createEntityManager()){
            Genre genre = em.find(Genre.class, genreId);
            if(genre == null)
                throw new EntityNotFoundException("No entity found with id: "+ genreId);
            em.getTransaction().begin();
            em.remove(genre);
            em.getTransaction().commit();
            return genre;
        }
    }

    @Override
    public Set<Genre> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g", Genre.class);
            return new HashSet<>(query.getResultList());
        }
    }
}

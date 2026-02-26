package app.dao;

import app.entities.Movie;
import app.services.EntityManagerFactoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class MovieDAO implements IDAO<Movie> {
    private static EntityManagerFactory emf = EntityManagerFactoryService.getEntityManagerFactory();


    @Override
    public Movie create(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        }
    }

    @Override
    public Movie getById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Movie movie = em.find(Movie.class, id);
            if (movie == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return movie;
        }
    }

    @Override
    public Movie update(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie updatedMovie = em.merge(movie);
            em.getTransaction().commit();
            return updatedMovie;
        }
    }

    @Override
    public Movie delete(long movieId) {
        try(EntityManager em = emf.createEntityManager()){
            Movie movie = em.find(Movie.class, movieId);
            if(movie == null)
                throw new EntityNotFoundException("No entity found with id: "+ movieId);
            em.getTransaction().begin();
            em.remove(movie);
            em.getTransaction().commit();
            return movie;
        }
    }

    @Override
    public Set<Movie> getAll() {
            try (EntityManager em = emf.createEntityManager()) {
                TypedQuery<Movie> query = em.createQuery("SELECT u FROM Movie u", Movie.class);
                return new HashSet<>(query.getResultList());
        }
    }

    /*
    public List<Movie> getAllMoviesByActor(Person person){
    try(entityManager = entityManagerFactory.createEntityManager()){

    TypedQuery<Movie> query = entityManager.createQuery("SELECT m FROM Movie m JOIN m.actorinmove a join a.person p WHERE p.id = :personId", Movie.class);
    return query.setParameter("personId", person.getId()).getResultList();
    }
    */
}

package app.dao;

import app.entities.Actor;
import app.entities.Crew;
import app.entities.Movie;
import app.entities.MovieActor;
import app.entities.MovieCrew;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class MovieDAO implements IDAO<Movie> {
    private static EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Movie create(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {

            Movie movieInDB = findByMovieId(em, movie.getMovieId());

            if (movieInDB != null) {
                System.out.println("Movie already exists with movieId: " + movie.getMovieId());
                return movieInDB;
            }
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        }
    }

    @Override
    public Movie getById(long id) {
        try (EntityManager em = emf.createEntityManager()) {
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
        try (EntityManager em = emf.createEntityManager()) {
            Movie movie = em.find(Movie.class, movieId);
            if (movie == null)
                throw new EntityNotFoundException("No entity found with id: " + movieId);
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



    private Movie findByMovieId(EntityManager em, long movieId) {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m WHERE m.movieId = :movieId", Movie.class);
        query.setParameter("movieId", movieId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Movie updateTitleAndReleaseDate(long movieId, String title, String releaseDate) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie movie = findByMovieId(em, movieId);
            if (movie == null) {
                throw new EntityNotFoundException("No entity found with movieId: " + movieId);
            }
            em.getTransaction().begin();
            if (title != null) movie.setTitle(title);
            if (releaseDate != null) movie.setReleaseDate(releaseDate);
            em.getTransaction().commit();
            return movie;
        }
    }

    public Movie deleteByMovieId(long movieId) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie movie = findByMovieId(em, movieId);
            if (movie == null) {
                throw new EntityNotFoundException("No entity found with movieId: " + movieId);
            }
            em.getTransaction().begin();
            em.remove(movie);
            em.getTransaction().commit();
            return movie;
        }
    }

    public Set<Movie> searchByTitle(String titlePart) {
        if (titlePart == null || titlePart.isBlank()) return new HashSet<>();
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m WHERE LOWER(m.title) LIKE :q", Movie.class);
            query.setParameter("q", "%" + titlePart.toLowerCase() + "%");
            return new HashSet<>(query.getResultList());
        }
    }

    public double getAverageRating() {
        try (EntityManager em = emf.createEntityManager()) {
            Double avg = em.createQuery("SELECT AVG(m.rating) FROM Movie m", Double.class)
                    .getSingleResult();
            return avg == null ? 0.0 : avg;
        }
    }

    public Set<Movie> getTop10HighestRated() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m ORDER BY m.rating DESC", Movie.class);
            query.setMaxResults(10);
            return new HashSet<>(query.getResultList());
        }
    }

    public Set<Movie> getTop10LowestRated() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m ORDER BY m.rating ASC", Movie.class);
            query.setMaxResults(10);
            return new HashSet<>(query.getResultList());
        }
    }

    public Set<Movie> getTop10MostPopular() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m ORDER BY m.popularity DESC", Movie.class);
            query.setMaxResults(10);
            return new HashSet<>(query.getResultList());
        }
    }

    public Set<Movie> getMoviesByActorId(long actorId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT DISTINCT m FROM Movie m JOIN m.cast ma JOIN ma.actor a WHERE a.actorId = :actorId",
                    Movie.class);
            query.setParameter("actorId", actorId);
            return new HashSet<>(query.getResultList());
        }
    }

    public Set<Movie> getMoviesByDirectorId(long crewId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT DISTINCT m FROM Movie m JOIN m.crew mc JOIN mc.crew c " +
                            "WHERE c.crewId = :crewId AND LOWER(mc.job) = 'director'",
                    Movie.class);
            query.setParameter("crewId", crewId);
            return new HashSet<>(query.getResultList());
        }
    }


}

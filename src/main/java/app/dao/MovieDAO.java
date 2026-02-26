package app.dao;

import app.entities.Actor;
import app.entities.Crew;
import app.entities.Movie;
import app.entities.MovieActor;
import app.entities.MovieCrew;
import app.services.EntityManagerFactoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
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

            Movie movieInDB = em.find(Movie.class, movie.getId());

            if (movieInDB != null) {
                System.out.println("Movie already exists with id: " + movie.getId());
                return movie;
            }
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

    public void createMovieAndActorRelation(long movieId, long actorId, String role) {
        try (EntityManager em = emf.createEntityManager()) {

            Movie movie = em.find(Movie.class, movieId);
            Actor actor = em.find(Actor.class, actorId);

            if (movie == null)
                throw new EntityNotFoundException("No entity found with id: " + movieId);
            if (actor == null)
                throw new EntityNotFoundException("No entity found with id: " + actorId);

            MovieActor movieActor = new MovieActor();
            movieActor.setMovie(movie);
            movieActor.setCharacter(role);

            movieActor.setActor(actor);
            em.getTransaction().begin();
            em.persist(movieActor);
            em.getTransaction().commit();

        }
    }

    public void createMovieAndCrewRelation(long movieId, long crewId, String job) {
        try (EntityManager em = emf.createEntityManager()) {

            Movie movie = em.find(Movie.class, movieId);
            Crew crew = em.find(Crew.class, crewId);

            if (movie == null)
                throw new EntityNotFoundException("No entity found with id: " + movieId);
            if (crew == null)
                throw new EntityNotFoundException("No entity found with id: " + crewId);

            MovieCrew movieCrew = new MovieCrew();
            movieCrew.setMovie(movie);
            movieCrew.setJob(job);

            movieCrew.setCrew(crew);
            em.getTransaction().begin();
            em.persist(movieCrew);
            em.getTransaction().commit();

        }
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

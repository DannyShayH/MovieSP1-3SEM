package app;

import app.dao.ActorDAO;
import app.dao.CrewDAO;
import app.dao.MovieDAO;
import app.dao.PersonDAO;
import app.dto.MovieDTO;
import app.dto.ActorInMovieDTO;
import app.entities.*;
import app.services.EntityManagerFactoryService;
import app.services.MovieFactory;
import app.services.MovieService;
import app.services.PersonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;


public class App {
    public static void initiate()
    {
//    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
//    MovieService movieService = new MovieService(emf);
//    movieService.getDanishMovies();

        /* Pseudocode:
        {
        Create all movies
        Get actor Ids from all movies
        Create actor from Id
        relate personalInformation based on id
        get crew Ids from all movies
        relate personalInformation based on id
        }
        pseudocode 2
        {
        Create all movies
        get crew and actor Ids from all movies
        create crew and actor from Id as id has begotten
        relate personalInformation based on id
        }

        when done

        relate movie to acter
        relate movie to crew

         */

        Movie movie = MovieFactory.createMovie();
        System.out.println(movie);

//        List<Actor> actor = PersonFactory.getActorsFromMovie(movie.getProductionDTO());




        for(PersonalInformation personalInformation : PersonFactory.getAllPeopleFromAMovie()) {
            PersonDAO personDAO = new PersonDAO();
            System.out.println(personalInformation);
            personDAO.create(personalInformation);
        }

        MovieDAO movieDAO = new MovieDAO(EntityManagerFactoryService.getEntityManagerFactory());
        movieDAO.create(movie);



    }


    public static void test() {


        List<MovieDTO> moviesDTO = MovieService.getDanishMovies();
        assert moviesDTO != null;
        List<Movie> movies = MovieFactory.createMovies(moviesDTO);
        for(Movie movie : movies) {
            MovieDAO movieDAO = new MovieDAO(EntityManagerFactoryService.getEntityManagerFactory());
            movieDAO.create(movie);
        }


    }


//    public static void main(String[] args) {
//
//
//
//        CrewDAO crewDAO = new CrewDAO(EntityManagerFactoryService.getEntityManagerFactory());
//        ActorDAO actorDAO = new ActorDAO(EntityManagerFactoryService.getEntityManagerFactory());
//
//
//        Actor actor = new Actor();
//        actor.setActorId(1L);
//
//        PersonalInformation personalInformation = new PersonalInformation();
//        personalInformation.setPersonId(1L);
//        personalInformation.setName("test");
//        personalInformation.setBiography("test");
//        actor.setPersonalInformation(personalInformation);
//
//
//
//
//
//        System.out.println(actor);
//
//        Actor returnedActor = actorDAO.create(actor);
//
//        System.out.println(returnedActor);
//
//        MovieActor movieActor = new MovieActor();
//
//        movieActor.setActor(actor);
//
//        System.out.println("____________________________"+ "\n");
//
//        Crew crew = new Crew();
//        PersonalInformation personalInformation1 = new PersonalInformation();
//        personalInformation1.setPersonId(2L);
//        personalInformation1.setName("crew_test");
//        personalInformation1.setBiography("crew_test");
//        crew.setPersonalInformation(personalInformation1);
//
//        System.out.println(crew);
//
//        crew.setCrewId(1L);
//        Crew returnedCrew = crewDAO.create(crew);
//        MovieCrew movieCrew = new MovieCrew();
//        movieCrew.setCrew(crew);
//
//        System.out.println(returnedCrew);
//
//        MovieDAO movieDAO = new MovieDAO(EntityManagerFactoryService.getEntityManagerFactory());
//        Movie movie1 = new Movie();
//        movie1.setTitle("test");
//        movie1.setRating(1.0);
//        movie1.setOverview("test");
//        movie1.setReleaseDate("2023-01-01");
//        movie1.setId(1L);
//
//        movie1 = movieDAO.create(movie1);
//
//
//
//        movieActor.setMovie(movie1);
//        movieDAO.createMovieAndActorRelation(movie1.getId(), returnedActor.getActorId(), "testActorAndMovieRelation");
//
//        movieCrew.setMovie(movie1);
//        movieDAO.createMovieAndCrewRelation(movie1.getId(), returnedCrew.getCrewId(), "testCrewAndMovieRelation");
//
//
//        System.out.println(movieActor);
//
//
//    }

}

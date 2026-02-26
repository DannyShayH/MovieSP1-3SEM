package app;

import app.config.HibernateConfig;
import app.dao.ActorDAO;
import app.dao.MovieDAO;
import app.dao.PersonDAO;
import app.entities.Actor;
import app.entities.Movie;
import app.entities.PersonalInformation;
import app.services.EntityManagerFactoryService;
import app.services.MovieFactory;
import app.services.PersonFactory;
import jakarta.persistence.EntityManagerFactory;


public class App {
    public static void initiate()
    {
//    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
//    MovieService movieService = new MovieService(emf);
//    movieService.getDanishMovies();


        Movie movie = MovieFactory.createMovie();
        System.out.println(movie);


        for(PersonalInformation personalInformation : PersonFactory.getAllPeopleFromAllMovies()) {
            PersonDAO personDAO = new PersonDAO();
            System.out.println(personalInformation);
            personDAO.create(personalInformation);
        }
        MovieDAO movieDAO = new MovieDAO();
        movieDAO.create(movie);


    }


    public static void main(String[] args) {



        ActorDAO actorDAO = new ActorDAO(EntityManagerFactoryService.getEntityManagerFactory());
        Actor actor = new Actor();
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setPersonId(1L);
        personalInformation.setName("test");
        personalInformation.setBiography("test");
        actor.setActorId(1L);
        actor.setPersonalInformation(personalInformation);
        System.out.println(actor);
        Actor returnedActor = actorDAO.create(actor);
        System.out.println(returnedActor);

    }
}

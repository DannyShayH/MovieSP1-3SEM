package app;

import app.dao.MovieDAO;
import app.dto.MovieDTO;
import app.entities.Movie;
import app.services.EntityManagerFactoryService;
import app.services.MovieFactory;
import app.services.MovieService;

public class App {
    public static void initiate()
    {
//    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
//    MovieService movieService = new MovieService(emf);
//    movieService.getDanishMovies();


        Movie movie = MovieFactory.createMovie();
        System.out.println(movie);


        for(Person person : PersonFactory.getAllPeopleFromAllMovies()) {
            PersonDAO personDAO = new PersonDAO();
            System.out.println(person);
            personDAO.create(person);
        }
        MovieDAO movieDAO = new MovieDAO();
        movieDAO.create(movie);


    }
}

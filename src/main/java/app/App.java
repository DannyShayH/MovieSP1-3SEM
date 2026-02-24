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

        MovieDAO movieDAO = new MovieDAO();
        movieDAO.create(movie);

    }
}

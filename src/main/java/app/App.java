package app;

import app.dao.MovieDAO;
import app.dao.PersonalInfoDAO;
import app.dto.MovieDTO;
import app.entities.*;
import app.services.*;

import java.util.List;


public class App {
    public static void initiate()
    {
        MovieSyncService.syncFromTmdb();

        MovieDAO movieDAO = new MovieDAO(EntityManagerFactoryService.getEntityManagerFactory());
        movieDAO.getAll().forEach(System.out::println);
        movieDAO.getTop10LowestRated().forEach(System.out::println);
        movieDAO.getTop10MostPopular().forEach(System.out::println);
        movieDAO.getTop10HighestRated().forEach(System.out::println);
        movieDAO.getMoviesByActorId(1).forEach(System.out::println);

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
}

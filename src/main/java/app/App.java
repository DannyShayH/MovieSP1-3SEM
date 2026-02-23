package app;


import app.config.HibernateConfig;
import app.dao.MovieDAO;
import app.services.MovieService;
import app.utils.ApiFetcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;

public class App {
    public static void initiate()
    {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    MovieService movieService = new MovieService(emf);
    movieService.getDanishMovies();


        MovieDAO movie = new  MovieDAO();
        movie.create();

    }
}

package app.services.ApiServices;

import app.dao.MovieDAO;
import app.dto.MovieDTO;
import app.entities.Movie;
import app.services.persistenceServices.EntityManagerFactoryService;
import app.services.persistenceServices.MovieFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieSyncService {

    public static void syncFromTmdb()
    {
        MovieService movieService = new MovieService();
        List<MovieDTO> apiMovies = movieService.getDanishMovies();
        List<Movie> moviesToPersist = MovieFactory.createMovies(apiMovies);
        MovieDAO movieDAO = new MovieDAO(EntityManagerFactoryService.getEntityManagerFactory());
        Set<Long> apiMovieIds = new HashSet<>();

        for (MovieDTO dto : apiMovies)
        {
            apiMovieIds.add(dto.getId());
        }

        Set<Movie> existingMovies = movieDAO.getAll();
        Set<Long> dbMovieIds = new HashSet<>();
        for (Movie movie : existingMovies)
        {
            dbMovieIds.add(movie.getMovieId());
        }

        for (Movie movie : moviesToPersist)
        {
            if (!dbMovieIds.contains(movie.getMovieId()))
            {
                movieDAO.create(movie);
            }
        }

        for (Long movieId : dbMovieIds)
        {
            if (!apiMovieIds.contains(movieId))
            {
                movieDAO.deleteByMovieId(movieId);
            }
        }
    }
}

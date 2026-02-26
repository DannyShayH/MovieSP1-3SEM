package app.services;

import app.dao.ActorDAO;
import app.dao.PersonDAO;
import app.dto.ActorInMovieDTO;
import app.dto.CrewInMovieDTO;
import app.dto.MovieDTO;
import app.dto.ProductionDTO;
import app.entities.Actor;
import app.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieFactory {


    public static Movie createMovie(){


//        for (Genre genre : GenreAPIFactory.getGenreApiService().getAllGenres()){
//            GenreDAO genreDAO = new GenreDAO();
//            genreDAO.create(genre);
//        }
        Movie movie = new Movie();

        for(MovieDTO movieDTO : MovieService.getDanishMovies()) {
            movie.setTitle(movieDTO.getTitle());
            movie.setRating(movieDTO.getRating());
            movie.setOverview(movieDTO.getOverview());
            movie.setReleaseDate(movieDTO.getReleaseDate());
            movie.setMovieId(movieDTO.getId());
            for (long id : movieDTO.getGenres()) {
                movie.addGenre(GenreAPIFactory.getGenreApiService().getGenreById(id));
            }


            ProductionDTO totalCast = MovieService.getProductionTeam(String.valueOf(movieDTO.getId()));
            PersonFactory.addPeopleToList(totalCast);

            ActorDAO personDAO = new ActorDAO(EntityManagerFactoryService.getEntityManagerFactory());
            for (Actor actor : PersonFactory.getActorsFromMovie(totalCast)) {
                personDAO.create(actor);
            }

        }

        return movie;
    }
    public static List<Movie> createMovies(List<MovieDTO> movies){
     List<Movie> movieList = new ArrayList<>();

//        for (Genre genre : GenreAPIFactory.getGenreApiService().getAllGenres()){
//            GenreDAO genreDAO = new GenreDAO();
//            genreDAO.create(genre);
//        }
        for(MovieDTO movieDTO : movies) {
            Movie movie = new Movie();

            movie.setTitle(movieDTO.getTitle());
            movie.setRating(movieDTO.getRating());
            movie.setOverview(movieDTO.getOverview());
            movie.setReleaseDate(movieDTO.getReleaseDate());
            movie.setMovieId(movieDTO.getId());
            for (long id : movieDTO.getGenres()) {
                movie.addGenre(GenreAPIFactory.getGenreApiService().getGenreById(id));
            }
            ProductionDTO totalCast = MovieService.getProductionTeam(String.valueOf(movieDTO.getId()));
            PersonFactory.addPeopleToList(totalCast);
            movieList.add(movie);
        }


        return movieList;
    }
}

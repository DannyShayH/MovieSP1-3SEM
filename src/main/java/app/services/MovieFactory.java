package app.services;

import app.dto.ActorInMovieDTO;
import app.dto.CrewInMovieDTO;
import app.dto.MovieDTO;
import app.dto.ProductionDTO;
import app.entities.Movie;

import java.util.List;

public class MovieFactory {


    public static Movie createMovie(){
        MovieDTO movieDTO = MovieService.getMovie();

//        for (Genre genre : GenreAPIFactory.getGenreApiService().getAllGenres()){
//            GenreDAO genreDAO = new GenreDAO();
//            genreDAO.create(genre);
//        }
        Movie movie = new Movie();

        movie.setTitle(movieDTO.getTitle());
        movie.setRating(movieDTO.getRating());
        movie.setOverview(movieDTO.getOverview());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setId(movieDTO.getId());
        for(long id : movieDTO.getGenres()){
            movie.addGenre(GenreAPIFactory.getGenreApiService().getGenreById(id));
        }


        ProductionDTO totalCast = MovieService.getProductionTeam(String.valueOf(movieDTO.getId()));
        PersonFactory.addPeopleToList(totalCast);
        List<ActorInMovieDTO> cast = totalCast.getCast();
        List<CrewInMovieDTO> crew = totalCast.getCrew();
        movie.setCast(ProductionService.actorConverter(cast));
        movie.setCrew(ProductionService.crewConverter(crew));

        return movie;
    }
}

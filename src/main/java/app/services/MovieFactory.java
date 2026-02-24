package app.services;

import app.dto.ActorDTO;
import app.dto.CrewDTO;
import app.dto.MovieDTO;
import app.dto.ProductionDTO;
import app.entities.Actor;
import app.entities.Movie;
import app.interfaces.IEntity;
import app.interfaces.IPerson;

import java.util.Collections;
import java.util.List;
import app.entities.Actor;
public class MovieFactory {

    public static Movie createMovie(){
        MovieDTO movieDTO = MovieService.getMovie();
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setRating(movieDTO.getRating());
        movie.setOverview(movieDTO.getOverview());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setId(movieDTO.getId());

        ProductionDTO totalCast = MovieService.getProductionTeam(String.valueOf(movieDTO.getId()));
        List<ActorDTO> cast = totalCast.getCast();
        List<CrewDTO> crew = totalCast.getCrew();
        movie.setCast(ProductionService.actorConverter(cast));
        movie.setCrew(ProductionService.crewConverter(crew));

        return movie;
    }
}

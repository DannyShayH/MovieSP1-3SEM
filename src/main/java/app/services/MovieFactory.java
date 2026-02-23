package app.services;

import app.dto.MovieDTO;
import app.entities.Movie;

public class MovieFactory {

    public static Movie createMovie(){
        MovieDTO movieDTO = MovieService.getMovie();
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setRating(movieDTO.getRating());
        movie.setOverview(movieDTO.getOverview());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        return movie;
    }
}

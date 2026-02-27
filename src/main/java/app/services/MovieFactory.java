package app.services;

import app.dao.ActorDAO;
import app.dao.CrewDAO;
import app.dto.MovieDTO;
import app.dto.ProductionDTO;
import app.entities.Actor;
import app.entities.Crew;
import app.entities.Movie;
import app.entities.PersonalInformation;
import app.dao.PersonalInfoDAO;

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
            ActorDAO actorDAO = new ActorDAO(EntityManagerFactoryService.getEntityManagerFactory());

            for (Actor actor : PersonFactory.getActorsFromMovie(totalCast)) {


                actor.setPersonalInformation(PersonFactory.setAnActorsPersonalInformation(actor));

                actorDAO.create(actor);

//            for (PersonalInformation personalInformation : PersonFactory.getAllPeopleFromAMovie()) {
//                if (personalInformation.getPersonId() == actor.getActorId()) {
//                    actor.setPersonalInformation(personalInformation);
//                }
//            }
            }
                CrewDAO crewDAO = new CrewDAO(EntityManagerFactoryService.getEntityManagerFactory());
            for (Crew crew : PersonFactory.getCrewFromMovie(totalCast)) {
                crew.setPersonalInformation(PersonFactory.setACrewsPersonalInformation(crew));
                crewDAO.create(crew);
            }

            movieList.add(movie);
        }

        return movieList;
    }
}

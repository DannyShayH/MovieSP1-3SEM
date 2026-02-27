package app.services.persistenceServices;

import app.dao.ActorDAO;
import app.dao.CrewDAO;
import app.dto.MovieDTO;
import app.dto.ProductionDTO;
import app.dto.ActorInMovieDTO;
import app.dto.CrewInMovieDTO;
import app.entities.Actor;
import app.entities.Crew;
import app.entities.Movie;
import app.entities.MovieActor;
import app.entities.MovieCrew;
import app.services.ApiServices.GenreAPIFactory;
import app.services.ApiServices.MovieService;

import java.util.ArrayList;
import java.util.List;

public class MovieFactory {


    public static List<Movie> createMovies(List<MovieDTO> movies)
    {
        List<Movie> movieList = new ArrayList<>();

        for (MovieDTO movieDTO : movies)
        {
            Movie movie = new Movie();

            movie.setTitle(movieDTO.getTitle());
            movie.setRating(movieDTO.getRating());
            movie.setOverview(movieDTO.getOverview());
            movie.setReleaseDate(movieDTO.getReleaseDate());
            movie.setMovieId(movieDTO.getId());
            movie.setPopularity(movieDTO.getPopularity());

            for (long id : movieDTO.getGenres())
            {
                movie.addGenre(GenreAPIFactory.getGenreApiService().getGenreById(id));
            }

            MovieService movieService = new MovieService();

            ProductionDTO totalCast = movieService.getProductionTeam(String.valueOf(movieDTO.getId()));

            ActorDAO actorDAO = new ActorDAO(EntityManagerFactoryService.getEntityManagerFactory());
            CrewDAO crewDAO = new CrewDAO(EntityManagerFactoryService.getEntityManagerFactory());
            List<ActorInMovieDTO> cast = totalCast.getCast();
            List<CrewInMovieDTO> crew = totalCast.getCrew();

            for (ActorInMovieDTO actorDTO : cast)
            {
                Actor actor = actorDAO.findByActorId(actorDTO.getId());
                if (actor == null)
                {
                    Actor newActor = new Actor();
                    newActor.setActorId(actorDTO.getId());
                    newActor.setPersonalInformation(
                            PersonFactory.setAnActorsPersonalInformation(newActor)
                    );
                    actor = actorDAO.create(newActor);
                }

                MovieActor movieActor = new MovieActor();
                movieActor.setActor(actor);
                movieActor.setOriginalName(actorDTO.getOriginalName());
                movieActor.setCharacter(actorDTO.getCharacter());
                movie.addCastMember(movieActor);
            }

            for (CrewInMovieDTO crewDTO : crew)
            {
                Crew crewMember = crewDAO.findByCrewId(crewDTO.getId());
                if (crewMember == null)
                {
                    Crew newCrew = new Crew();
                    newCrew.setCrewId(crewDTO.getId());
                    newCrew.setPersonalInformation(
                            PersonFactory.setACrewsPersonalInformation(newCrew)
                    );
                    crewMember = crewDAO.create(newCrew);
                }

                MovieCrew movieCrew = new MovieCrew();
                movieCrew.setCrew(crewMember);
                movieCrew.setOriginalName(crewDTO.getOriginalName());
                movieCrew.setJob(crewDTO.getJob());
                movieCrew.setDepartment(crewDTO.getDepartment());
                movie.addCrewMember(movieCrew);
            }

                movieList.add(movie);


        }
        return movieList;
    }

}
//

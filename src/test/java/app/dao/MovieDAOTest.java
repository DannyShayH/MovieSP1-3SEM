package app.dao;

import app.entities.Actor;
import app.entities.Crew;
import app.entities.Movie;
import app.entities.MovieActor;
import app.entities.MovieCrew;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class MovieDAOTest extends DaoTestBase {

    @Test
    void create_preventsDuplicateMovieId() {
        MovieDAO dao = new MovieDAO(emf);

        Movie first = Movie.builder()
                .movieId(100)
                .title("First")
                .rating(7.5)
                .popularity(10.0)
                .build();

        Movie created1 = dao.create(first);

        Movie second = Movie.builder()
                .movieId(100)
                .title("Second")
                .rating(9.0)
                .build();

        Movie created2 = dao.create(second);

        assertEquals(created1.getId(), created2.getId());
        assertEquals("First", created2.getTitle());
    }

    @Test
    void searchByTitle_isCaseInsensitive() {
        MovieDAO dao = new MovieDAO(emf);

        dao.create(Movie.builder().movieId(1).title("The Thing").build());
        dao.create(Movie.builder().movieId(2).title("Another Movie").build());
        dao.create(Movie.builder().movieId(3).title("thing again").build());

        Set<Movie> results = dao.searchByTitle("ThInG");
        assertEquals(2, results.size());
    }

    @Test
    void getAverageRating_returnsMean() {
        MovieDAO dao = new MovieDAO(emf);

        dao.create(Movie.builder().movieId(1).rating(6.0).build());
        dao.create(Movie.builder().movieId(2).rating(8.0).build());

        assertEquals(7.0, dao.getAverageRating(), 0.0001);
    }

    @Test
    void getTop10HighestRated_excludesLowestTwo() {
        MovieDAO dao = new MovieDAO(emf);

        for (int i = 1; i <= 12; i++) {
            dao.create(Movie.builder()
                    .movieId(i)
                    .title("M" + i)
                    .rating(i)
                    .build());
        }

        Set<Movie> top = dao.getTop10HighestRated();
        assertEquals(10, top.size());
        double minRating = top.stream().mapToDouble(Movie::getRating).min().orElse(0.0);
        assertEquals(3.0, minRating, 0.0001);
    }

    @Test
    void getTop10LowestRated_excludesHighestTwo() {
        MovieDAO dao = new MovieDAO(emf);

        for (int i = 1; i <= 12; i++) {
            dao.create(Movie.builder()
                    .movieId(i)
                    .title("M" + i)
                    .rating(i)
                    .build());
        }

        Set<Movie> top = dao.getTop10LowestRated();
        assertEquals(10, top.size());
        double maxRating = top.stream().mapToDouble(Movie::getRating).max().orElse(0.0);
        assertEquals(10.0, maxRating, 0.0001);
    }

    @Test
    void getTop10MostPopular_excludesLowestTwo() {
        MovieDAO dao = new MovieDAO(emf);

        for (int i = 1; i <= 12; i++) {
            dao.create(Movie.builder()
                    .movieId(i)
                    .title("M" + i)
                    .popularity(i)
                    .build());
        }

        Set<Movie> top = dao.getTop10MostPopular();
        assertEquals(10, top.size());
        double minPopularity = top.stream().mapToDouble(Movie::getPopularity).min().orElse(0.0);
        assertEquals(3.0, minPopularity, 0.0001);
    }

    @Test
    void getMoviesByActorId_returnsLinkedMovies() {
        Actor actor = Actor.builder().actorId(200).build();
        new ActorDAO(emf).create(actor);

        Movie movie = Movie.builder().movieId(300).title("Actor Movie").build();
        MovieActor movieActor = new MovieActor();
        movieActor.setActor(actor);
        movie.addCastMember(movieActor);

        MovieDAO dao = new MovieDAO(emf);
        dao.create(movie);

        Set<Movie> results = dao.getMoviesByActorId(200);
        assertEquals(1, results.size());
        assertEquals(300, results.iterator().next().getMovieId());
    }

    @Test
    void getMoviesByDirectorId_matchesJobDirectorCaseInsensitive() {
        Crew crew = Crew.builder().crewId(400).build();
        new CrewDAO(emf).create(crew);

        Movie movie = Movie.builder().movieId(500).title("Director Movie").build();
        MovieCrew movieCrew = new MovieCrew();
        movieCrew.setCrew(crew);
        movieCrew.setJob("Director");
        movie.addCrewMember(movieCrew);

        MovieDAO dao = new MovieDAO(emf);
        dao.create(movie);

        Set<Movie> results = dao.getMoviesByDirectorId(400);
        assertEquals(1, results.size());
        assertEquals(500, results.iterator().next().getMovieId());
    }
}

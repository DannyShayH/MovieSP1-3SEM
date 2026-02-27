package app.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieEntityTest {

    @Test
    void addGenre_addsBothSides() {
        Movie movie = Movie.builder().movieId(1).title("M").build();
        Genre genre = new Genre(10, "Drama");

        movie.addGenre(genre);

        assertTrue(movie.getGenres().contains(genre));
        assertTrue(genre.getMovie().contains(movie));
    }

    @Test
    void addCastMember_setsBackReference() {
        Movie movie = Movie.builder().movieId(1).title("M").build();
        MovieActor cast = new MovieActor();

        movie.addCastMember(cast);

        assertTrue(movie.getCast().contains(cast));
        assertEquals(movie, cast.getMovie());
    }

    @Test
    void addCrewMember_setsBackReference() {
        Movie movie = Movie.builder().movieId(1).title("M").build();
        MovieCrew crew = new MovieCrew();

        movie.addCrewMember(crew);

        assertTrue(movie.getCrew().contains(crew));
        assertEquals(movie, crew.getMovie());
    }
}

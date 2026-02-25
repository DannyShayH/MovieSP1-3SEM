package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Genre  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="genre_id")
    private long genreId;

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movie =new HashSet<>();

    public Genre(long genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    public void addToMovieList(Movie movie) {

        this.movie.add(movie);
    }
}

package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "movie_id")
    private long movieId;

    @Column(length = 10000)
    private String title;
    @Column(length = 10000)
    private String overview;
    @Column(length = 10000)
    private String releaseDate;

    @Column(length = 10000)
    private double rating;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<MovieActor> cast;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<MovieCrew> crew;

    @ManyToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Genre> genres = new HashSet<>();


    @Override
    public String toString() {
        return "Title: " + title + "\n +" +
                "Overview: " + overview + "\n" +
                "Release Date: " + releaseDate + "\n" +
                "Rating: " + rating + "\n" ;
//                "Cast" + cast + "\n" +
//                "Crew" + crew;
    }

    public void addGenre(Genre genre) {
        if (genre != null) {
            if (genres.contains(genre)) {
                return;
            }
             genres.add(genre);
                genre.addToMovieList(this);

        }
    }
}

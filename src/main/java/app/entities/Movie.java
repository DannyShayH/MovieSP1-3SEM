package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Movie {

    @Id
    private long id;
    @Column(length = 10000)
    private String title;
    @Column(length = 10000)
    private String overview;
    @Column(length = 10000)
    private String releaseDate;

    private double rating;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ActorInMovie> cast;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CrewInMovie> crewInMovie;
    @ManyToMany(cascade = CascadeType.ALL)
    @Builder.Default
//    @JoinTable()
    private Set<Genre> genres = new HashSet<>();


    @Override
    public String toString() {
        return "Title: " + title + "\n +" +
                "Overview: " + overview + "\n" +
                "Release Date: " + releaseDate + "\n" +
                "Rating: " + rating + "\n" +
                "Cast" + cast + "\n" +
                "Crew" + crewInMovie;
    }
}

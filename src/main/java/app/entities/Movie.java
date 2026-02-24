package app.entities;

import app.interfaces.IEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private List<Actor> cast;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Crew> crew;

    @Override
    public String toString() {
        return "Title: " + title + "\n +" +
                "Overview: " + overview + "\n" +
                "Release Date: " + releaseDate + "\n" +
                "Rating: " + rating + "\n" +
                "Cast" + cast + "\n" +
                "Crew" + crew;
    }
}

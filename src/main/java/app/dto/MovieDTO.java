package app.dto;

import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {

    @JsonProperty("original_title")
    private String title;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private double rating;

    private Set<Genre> genre;

    private Set<Director> director;

    private Set<Actor> actor;

}

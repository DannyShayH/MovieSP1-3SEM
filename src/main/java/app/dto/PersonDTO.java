package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("birthday")
    private LocalDate birthday;

    @JsonProperty("deathday")
    private LocalDate deathday;

    @JsonProperty("biography")
    private String biography;

    @JsonProperty("place_of_birth")
    private String placeOfBirth;

    @JsonProperty("gender")
    private int gender;

    @JsonProperty("also_known_as")
    private Set<String> alsKnownAs;

    @JsonProperty("popularity")
    private double popularity;

    @JsonProperty("known_for_department")
    private String department;
}
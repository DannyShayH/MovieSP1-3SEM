package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class ActorInMovieDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("original_name")
    private String originalName;

    @JsonProperty("character")
    private String character;

}


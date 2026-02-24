package app.dto;

import app.interfaces.IPerson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductionDTO  {

    @JsonProperty("cast")
    private List<ActorDTO> cast;

    @JsonProperty("crew")
    private List<CrewDTO> crew;


}

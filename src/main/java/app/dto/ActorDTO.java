package app.dto;

import app.interfaces.IPerson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.tool.schema.extract.internal.InformationExtractorPostgreSQLImpl;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class ActorDTO  {
    @JsonProperty("known_for_department")
    private String department;

    @JsonProperty("id")
    private long id;

    @JsonProperty("gender")
    private int gender;

    @JsonProperty("name")
    private String name;

    @JsonProperty("original_name")
    private String originalName;

    @JsonProperty("character")
    private String character;


}


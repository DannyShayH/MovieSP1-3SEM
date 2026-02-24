package app.dto;

import app.interfaces.IPerson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewDTO {

    @JsonProperty("known_for_department")
    private String knownForDepartment;

    @JsonProperty("id")
    private long id;

    @JsonProperty("gender")
    private int gender;

    @JsonProperty("name")
    private String name;

    @JsonProperty("original_name")
    private String originalName;

    @JsonProperty("department")
    private String department;

    @JsonProperty("job")
    private String job;




}

package app.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private String title;
    private GenreDTO genre;
    private DirectorDTO director;
    private ActorDTO actor;


}

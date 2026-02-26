package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"movie", "crew"})
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class MovieCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 10000)
    private String originalName;

    @Column(length = 10000)
    private String job;

    @Column(length = 10000)
    private String department;


    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Crew crew;
}

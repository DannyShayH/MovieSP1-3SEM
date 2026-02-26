package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"movie", "actor"})
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class MovieActor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 10000)
    private String originalName;

    @Column(length = 10000)
    private String character;

@ManyToOne
private Movie movie;

@ManyToOne
private Actor actor;
}

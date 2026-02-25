package app.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@Entity
public class ActorInMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "actor_id")
    private long ActorId;

    @Column(length = 10000)
    private String name;

    @Column(length = 10000)
    private String originalName;

    @Column(length = 10000)
    private int gender;

    @Column(length = 10000)
    private String character;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "actor")
    private Person person;

    public ActorInMovie(Long actorId, String originalName, String character) {
        this.actorId = actorId;
        this.originalName = originalName;
        this.gender = gender;
        this.character = character;
    }

    @Override
    public String toString() {
        return "Original Name: " + originalName + "\n" +
                "Character: " + character;
    }
}

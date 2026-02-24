package app.entities;

import app.interfaces.IEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@Entity
@Data
@RequiredArgsConstructor
public class Actor {

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

    @JsonProperty("character")
    private String character;

    public Actor(Long id, String name, String originalName, int gender, String character) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.gender = gender;
        this.character = character;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Original Name: " + originalName + "\n" +
                "Gender: " + gender + "\n" +
                "Character: " + character;
    }
}

package app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class PersonalInformation {


    @Id
    @Column(name = "id")
    private long id;

    @Column(name ="personal_id", unique = true)
    private long personId;

    @Column(name = "name", length = 1000)
    private String name;

    @Column(name="gender", length = 1000)
    private int gender;

    @Column(name="birthday")
    private LocalDate birthday;

    @Column(name="deathday")
    private LocalDate deathday;

    @Column(name="known_for_department", length = 1000)
    private String knownForDepartment;

    @Column(name="also_known_as",length = 1000)
    private Set<String> knownAs;

    @Column(name="biography",length = 10000)
    private String biography;

    @Column(name="place_of_birth",length = 1000)
    private String birthPlace;

    @Column(name = "popularity")
    private double popularity;

    @OneToOne
    private Actor actor;

    @OneToOne
    private Crew crew;


    /*
    @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE)
    private Set<ActorInMovie> actorInMovie;
    @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE)
    private Set<CrewInMovie> crewInMovie;
    */

}

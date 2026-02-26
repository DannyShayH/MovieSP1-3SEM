package app.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "crew_id")
    private long crewId;

    @Column(length = 10000)
    private String originalName;

    @Column(length = 10000)
    private String job;

     @Column(length = 10000)
     private String department;

    @OneToOne(mappedBy = "crew", cascade = CascadeType.ALL)
    private PersonalInformation personalInformation;

}

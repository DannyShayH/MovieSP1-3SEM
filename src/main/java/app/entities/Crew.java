package app.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Entity
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "crew_id")
    private long crewId;

    @OneToOne(mappedBy = "crew", cascade = CascadeType.ALL)
    private PersonalInformation personalInformation;

}

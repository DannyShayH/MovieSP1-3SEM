package app.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Builder
@Table(name = "crew", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))

public class CrewInMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "crew_id")
    private long crewId;

    @Column(name = "crew_name")
    private String name;

    @Column(name = "job")
    private String job;

    @ManyToOne
    @JoinColumn(name = "crew")
    private Person person;

}

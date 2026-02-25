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

    @Column(length = 10000)
    private String originalName;

    @Column(length = 10000)
    private String job;

     @Column(length = 10000)
     private String department;


    @ManyToOne
    @JoinColumn(name = "crew")
    private Person person;

}

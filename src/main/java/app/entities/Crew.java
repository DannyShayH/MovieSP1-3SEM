package app.entities;

import app.dao.CrewDAO;
import app.interfaces.IEntity;
import app.interfaces.IPerson;
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

public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "crew_id")
    private long crewId;

    @Column(name = "crew_name")
    private String name;

    @Column(name = "job")
    private String job;

    @Column(name = "department")
    private String department;

    @Column(name = "gender")
    private int gender;


    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Job: " + job + "\n" +
                "Department: " + department + "\n" +
                "Gender: " + gender;
    }

//    @PrePersist
//    public void prePersist(){
//        if(this.crewId == CrewDAO;
//    }



}

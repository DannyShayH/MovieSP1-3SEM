package app.entities;

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
public class Director {

    @Id
    private long id;

    @Column(name = "director_name")
    private String name;

    @Column(name = "gender")
    private String gender;


}

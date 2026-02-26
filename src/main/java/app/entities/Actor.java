package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = "personalInformation")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "actor_id")
    private long actorId;

    @OneToOne(mappedBy = "actor", cascade = CascadeType.ALL)
    private PersonalInformation personalInformation;


}



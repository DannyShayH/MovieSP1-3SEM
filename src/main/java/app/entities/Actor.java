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

    @Column(name = "actor_id", unique = true)
    private long actorId;

//    @OneToOne(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private PersonalInformation personalInformation;





    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_id", unique = true)
    private PersonalInformation personalInformation;
}






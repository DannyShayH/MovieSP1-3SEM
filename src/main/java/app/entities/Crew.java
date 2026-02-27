package app.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "personalInformation")
@Builder
@Entity
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "crew_id", unique = true)
    private long crewId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_id", unique = true)
    private PersonalInformation personalInformation;
/*


    //@OneToOne(mappedBy = "crew", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    //private PersonalInformation personalInformation;
*/









}




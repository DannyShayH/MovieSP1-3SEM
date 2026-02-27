package app.dao;

import app.entities.Actor;
import app.entities.PersonalInformation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class ActorDAOTest extends DaoTestBase {

    @Test
    void create_upsertsPersonalInformationByPersonId() {
        ActorDAO dao = new ActorDAO(emf);

        PersonalInformation pi1 = PersonalInformation.builder()
                .personId(900)
                .name("Alice")
                .gender(1)
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        Actor actor1 = Actor.builder()
                .actorId(100)
                .personalInformation(pi1)
                .build();

        dao.create(actor1);

        PersonalInformation pi2 = PersonalInformation.builder()
                .personId(900)
                .name("Alice Updated")
                .gender(2)
                .birthday(LocalDate.of(1991, 2, 2))
                .build();

        Actor actor2 = Actor.builder()
                .actorId(100)
                .personalInformation(pi2)
                .build();

        dao.create(actor2);

        Actor reloaded = dao.findByActorId(100);
        assertNotNull(reloaded);
        assertNotNull(reloaded.getPersonalInformation());
        assertEquals("Alice Updated", reloaded.getPersonalInformation().getName());
        assertEquals(2, reloaded.getPersonalInformation().getGender());
        assertEquals(LocalDate.of(1991, 2, 2), reloaded.getPersonalInformation().getBirthday());
    }
}

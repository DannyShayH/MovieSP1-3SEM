package app.dao;

import app.entities.Crew;
import app.entities.PersonalInformation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class CrewDAOTest extends DaoTestBase {

    @Test
    void create_upsertsPersonalInformationByPersonId() {
        CrewDAO dao = new CrewDAO(emf);

        PersonalInformation pi1 = PersonalInformation.builder()
                .personId(910)
                .name("Bob")
                .gender(1)
                .birthday(LocalDate.of(1980, 5, 5))
                .build();

        Crew crew1 = Crew.builder()
                .crewId(200)
                .personalInformation(pi1)
                .build();

        dao.create(crew1);

        PersonalInformation pi2 = PersonalInformation.builder()
                .personId(910)
                .name("Bob Updated")
                .gender(0)
                .birthday(LocalDate.of(1981, 6, 6))
                .build();

        Crew crew2 = Crew.builder()
                .crewId(200)
                .personalInformation(pi2)
                .build();

        dao.create(crew2);

        Crew reloaded = dao.findByCrewId(200);
        assertNotNull(reloaded);
        assertNotNull(reloaded.getPersonalInformation());
        assertEquals("Bob Updated", reloaded.getPersonalInformation().getName());
        assertEquals(0, reloaded.getPersonalInformation().getGender());
        assertEquals(LocalDate.of(1981, 6, 6), reloaded.getPersonalInformation().getBirthday());
    }
}

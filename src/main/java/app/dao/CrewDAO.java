package app.dao;

import app.entities.Actor;
import app.entities.Crew;
import app.entities.PersonalInformation;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CrewDAO implements IDAO<Crew> {
    private static EntityManagerFactory emf;
    private EntityManager em;

    public CrewDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }



    @Override
    public Crew create(Crew crew) {
        if (crew == null) return null;

        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            try {
                long crewId = crew.getCrewId(); // use the business key on Crew

                Crew existing = findByCrewId(em, crewId);

                if (existing != null) {
                    // Update existing managed entity
                    PersonalInformation newPi = upsertPersonalInformation(em, crew.getPersonalInformation());
                    existing.setPersonalInformation(newPi);

                    if (newPi != null) {
                        newPi.setCrew(existing); // keep both sides in sync (if PI has crew backref)
                    }

                    // copy other updatable fields from crew -> existing here
                    // existing.setName(crew.getName());

                    tx.commit();
                    return existing;
                } else {
                    // Create new
                    PersonalInformation pi = upsertPersonalInformation(em, crew.getPersonalInformation());
                    crew.setPersonalInformation(pi);
                    if (pi != null) pi.setCrew(crew); // sync both sides

                    em.persist(crew); // cascades persist to PI if mapped that way
                    tx.commit();
                    return crew;
                }
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }
//    @Override
//    public Crew create(Crew crew) {
//        try (EntityManager em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            PersonalInformation pi = crew.getPersonalInformation();
//
//
//            if (getCrewInformationBoolean(pi.getCrew().getCrewId())) {
//                crew.setPersonalInformation(pi);
//                em.persist(crew);
//                em.merge(pi);
//                em.getTransaction().commit();
//                return crew;
//            }
//        }
//
//        return crew;
//
//        }




    private Crew findByCrewId(EntityManager em, long crewId) {
        List<Crew> result = em.createQuery(
                        "SELECT c FROM Crew c LEFT JOIN FETCH c.personalInformation WHERE c.crewId = :id",
                        Crew.class
                ).setParameter("id", crewId)
                .setMaxResults(1)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    private PersonalInformation upsertPersonalInformation(EntityManager em, PersonalInformation incoming) {
        if (incoming == null) return null;
        if (incoming.getPersonId() == 0) return incoming;

        List<PersonalInformation> found = em.createQuery(
                        "SELECT p FROM PersonalInformation p WHERE p.personId = :pid",
                        PersonalInformation.class
                ).setParameter("pid", incoming.getPersonId())
                .setMaxResults(1)
                .getResultList();

        if (found.isEmpty()) {
            return incoming;
        }

        PersonalInformation existing = found.get(0);
        existing.setName(incoming.getName());
        existing.setGender(incoming.getGender());
        existing.setBirthday(incoming.getBirthday());
        existing.setDeathday(incoming.getDeathday());
        existing.setKnownForDepartment(incoming.getKnownForDepartment());
        existing.setKnownAs(incoming.getKnownAs());
        existing.setBiography(incoming.getBiography());
        existing.setBirthPlace(incoming.getBirthPlace());
        existing.setPopularity(incoming.getPopularity());
        return existing;
    }
    @Override
    public Crew getById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Crew crew = em.find(Crew.class, id);
            if (crew == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return crew;
        }
    }

    @Override
    public Crew update(Crew crew) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Crew updatedCrew = em.merge(crew);
            em.getTransaction().commit();
            return updatedCrew;
        }
    }

    @Override
    public Crew delete(long crewId) {
        try(EntityManager em = emf.createEntityManager()){
            Crew crew = em.find(Crew.class, crewId);
            if(crew == null)
                throw new EntityNotFoundException("No entity found with id: "+ crewId);
            em.getTransaction().begin();
            em.remove(crew);
            em.getTransaction().commit();
            return crew;
        }
    }

    @Override
    public Set<Crew> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Crew> query = em.createQuery("SELECT c FROM Crew c", Crew.class);
            return new HashSet<>(query.getResultList());
        }
    }

    public Crew findByCrewId(long crewId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Crew> query = em.createQuery("SELECT c FROM Crew c WHERE c.crewId = :crewId", Crew.class);
            query.setParameter("crewId", crewId);
            try {
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }
}

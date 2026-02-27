package app.dao;

import app.entities.Actor;
import app.entities.PersonalInformation;
import app.services.EntityManagerFactoryService;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActorDAO implements IDAO<Actor> {
    private static EntityManagerFactory emf;
    private EntityManager em;


    public ActorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }



    @Override
    public Actor create(Actor actor) {
        if (actor == null) return null;

        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            try {
                long actorId = actor.getActorId();

                // Find existing actor by business key
                List<Actor> found = em.createQuery(
                                "SELECT a FROM Actor a LEFT JOIN FETCH a.personalInformation WHERE a.actorId = :id",
                                Actor.class)
                        .setParameter("id", actorId)
                        .setMaxResults(1)
                        .getResultList();

                if (!found.isEmpty()) {
                    // Update existing (preferred: update managed entity, not persist)
                    Actor managed = found.get(0);

                    PersonalInformation pi = upsertPersonalInformation(em, actor.getPersonalInformation());
                    managed.setPersonalInformation(pi);
                    if (pi != null) {
                        pi.setActor(managed); // keep both sides in sync
                    }

                    // update other Actor fields as needed here (but DO NOT change actorId if it's a business key)
                    // managed.setX(actor.getX());

                    tx.commit();
                    return managed;
                } else {
                    // Create new
                    PersonalInformation pi = upsertPersonalInformation(em, actor.getPersonalInformation());
                    actor.setPersonalInformation(pi);
                    if (pi != null) pi.setActor(actor); // sync both sides
                    em.persist(actor);                  // cascades persist to PI
                    tx.commit();
                    return actor;
                }
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }
//    @Override
//    public Actor create(Actor actor) {
//        try (EntityManager em = emf.createEntityManager()) {
//            if (!getActorInformationBoolean(actor.getActorId())) { //if(personalInformationInDB == false personalInformationInDB != null)
//                System.out.println("creating the same entity again:");
//                return actor;
//            }
//
//            em.getTransaction().begin();
//            PersonalInformation pi = actor.getPersonalInformation();
//            if (pi != null) {
//                PersonalInformation existing = em.find(PersonalInformation.class, pi.getPersonId());
//                if(existing == null){
//                    em.persist(pi);
//                }else{
//                    actor.setPersonalInformation(existing);
//                }
//            }
//            em.persist(actor);
//            em.getTransaction().commit();
//            return actor;
//        }
//    }

//    @Override
//    public Actor create(Actor actor){
//        try(EntityManager em = emf.createEntityManager()){
//            em.getTransaction().begin();
//            PersonalInformation pi = actor.getPersonalInformation();
////
//                if(actorExists(em, pi.getActor().getActorId())){
//                    actor.setPersonalInformation(pi);
//                    em.persist(actor);
//                    em.merge(pi);
//                    em.getTransaction().commit();
//                    return actor;
//                }
//            }
//            return actor;
//        }
////    }

    private boolean actorExists(EntityManager em, long actorId) {
        Long count = em.createQuery(
                        "SELECT COUNT(a) FROM Actor a WHERE a.actorId = :id", Long.class)
                .setParameter("id", actorId)
                .getSingleResult();
        return count > 0;
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
    public Actor getById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            Actor actor = em.find(Actor.class, id);
            if (actor == null)
                throw new EntityNotFoundException("No entity found with id: " + id);
            return actor;
        }
    }

    @Override
    public Actor update(Actor actor) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Actor updatedActor = em.merge(actor);
            em.getTransaction().commit();
            return updatedActor;
        }
    }

    @Override
    public Actor delete(long actorId) {
        try(EntityManager em = emf.createEntityManager()){
            Actor actor = em.find(Actor.class, actorId);
            if(actor == null)
                throw new EntityNotFoundException("No entity found with id: "+ actorId);
            em.getTransaction().begin();
            em.remove(actor);
            em.getTransaction().commit();
            return actor;
        }
    }

    @Override
    public Set<Actor> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Actor> query = em.createQuery("SELECT a FROM Actor a", Actor.class);
            return new HashSet<>(query.getResultList());
        }
    }

    public Actor findByActorId(long actorId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Actor> query = em.createQuery("SELECT a FROM Actor a WHERE a.actorId = :actorId", Actor.class);
            query.setParameter("actorId", actorId);
            try {
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

}

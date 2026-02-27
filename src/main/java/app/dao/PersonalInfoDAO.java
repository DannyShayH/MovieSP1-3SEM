package app.dao;

import app.entities.PersonalInformation;
import app.services.EntityManagerFactoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonalInfoDAO implements IDAO<PersonalInformation> {
    private EntityManagerFactory emf;
    EntityManager em;


    public PersonalInfoDAO() {
        this.emf = EntityManagerFactoryService.getEntityManagerFactory();
    }


    @Override
    public PersonalInformation create(PersonalInformation pi) {
        if (pi == null) return null;

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            List<PersonalInformation> results = em.createQuery(
                            "SELECT x FROM PersonalInformation x WHERE x.personId = :personId",
                            PersonalInformation.class
                    ).setParameter("personId", pi.getPersonId())
                    .setMaxResults(1)
                    .getResultList();

            if (!results.isEmpty()) {
                PersonalInformation existing = results.get(0);
                pi.setId(existing.getId());
                pi = em.merge(pi);   // update existing row
            } else {
                em.persist(pi);      // insert new row
            }

            em.getTransaction().commit();
            return pi;
        }
    }

//    @Override
//    public PersonalInformation create(PersonalInformation personalInformation) {
//        try (EntityManager em = emf.createEntityManager()) {
//
//            em.getTransaction().begin();
//            PersonalInformation pi = personalInformation;
//            if (pi != null) {
//                // Find existing PersonalInformation by unique personId
//                TypedQuery<PersonalInformation> query = em.createQuery(
//                        "SELECT pi FROM PersonalInformation pi WHERE pi.personId = :personId", PersonalInformation.class);
//                query.setParameter("personId", pi.getPersonId());
//                PersonalInformation existingPi = null;
//                try {
//                    existingPi = query.getSingleResult();
//                } catch (Exception e) {
//                    // No result found, proceed to persist new PersonalInformation
//                }
//                if (existingPi != null) {
//                    // Use the existing PI and update if needed
//                    pi.setId(existingPi.getId());
//                    em.merge(pi); // optionally merge changes if pi differs
//                } else {
//                    em.persist(pi);
//                }
//
//            }
//            em.persist(personalInformation);
//            em.getTransaction().commit();
//            return personalInformation;
//        }
//
//    }
//            if (checkPersonalInformation(personalInformation).contains(personalInformation.getPersonId())) {
//                return personalInformation;
//            }
//            if (!getActorInformationBoolean(personalInformation.getPersonId())) { //if(personalInformationInDB == false personalInformationInDB != null)
//                System.out.println("creating the same personalInformation again:");
//                return personalInformation;
//            }
//
//            em.getTransaction().begin();
//            long pi = personalInformation.getPersonId();
//            if (pi != 0) {
////                PersonalInformation existing = em.find(PersonalInformation.class, pi);
//                if(!getActorInformationBoolean(pi)){
//                    em.persist(pi);
//                    em.getTransaction().commit();
//                    return personalInformation;
//                }
//            }
//
//            return personalInformation;
//        }
    //}

    private boolean getActorInformationBoolean(long personalInformationId) {
        em = emf.createEntityManager();
        try {
            TypedQuery<PersonalInformation> query = em.createQuery("SELECT pi FROM PersonalInformation pi WHERE pi.personId = :id", PersonalInformation.class);
            query.setParameter("id", personalInformationId);

            return query.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }

    private Set<Long> checkPersonalInformation(PersonalInformation personalInformation) {
        em = emf.createEntityManager();
        try {
            TypedQuery<PersonalInformation> query = em.createQuery("SELECT pi FROM PersonalInformation pi WHERE pi.personId = :id", PersonalInformation.class);
            query.setParameter("id", personalInformation.getPersonId());
            return new HashSet<>(query.getResultList().stream().map(PersonalInformation::getId).toList());
        } finally {
            em.close();
        }
    }
//    private PersonalInformation getPersonalInformation(long personalId) {
//        em = emf.createEntityManager();
//        try {
//
//            TypedQuery<PersonalInformation> query = em.createQuery("SELECT p FROM PersonalInformation p WHERE p.personId = :id", PersonalInformation.class);
//            query.setParameter("id", personalId);
//            return query.getSingleResult();
//            }
//        catch (Exception e) {
//            return null;
//        }
//        finally {
//            em.close();
//        }
//
//    }


    @Override
    public PersonalInformation getById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(PersonalInformation.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Set<PersonalInformation> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<PersonalInformation> query = em.createQuery("SELECT p FROM PersonalInformation p", PersonalInformation.class);
            return new HashSet<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public PersonalInformation update(PersonalInformation personalInformation) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            PersonalInformation updated = em.merge(personalInformation);
            em.getTransaction().commit();
            return updated;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Could not update entity", e);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonalInformation delete(long personId) {
        try (EntityManager em = emf.createEntityManager()) {
            PersonalInformation personalInformation = em.find(PersonalInformation.class, personId);
            if (personalInformation == null)
                throw new EntityNotFoundException("No entity found with id: " + personId);
            em.getTransaction().begin();
            em.remove(personalInformation);
            em.getTransaction().commit();
            return personalInformation;
        }
    }

}




package app.dao;

import app.entities.PersonalInformation;
import app.services.persistenceServices.EntityManagerFactoryService;
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




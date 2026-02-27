package app.dao;

import app.entities.PersonalInformation;
import app.services.EntityManagerFactoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class PersonalInfoDAO implements IDAO<PersonalInformation> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public PersonalInfoDAO() {
        this.emf = EntityManagerFactoryService.getEntityManagerFactory();
    }

    @Override
    public PersonalInformation create(PersonalInformation personalInformation) {

        em = emf.createEntityManager();
//        PersonalInformation personalInformationInDB = getPersonalInformation(personalInformation.getPersonId());
        if (getPersonalInformationBoolean(personalInformation.getPersonId())) { //if(personalInformationInDB == false personalInformationInDB != null)
            System.out.println("creating the same entity again:");
            em.close();
            return personalInformation;
        }
        try {

            em.getTransaction().begin();
            em.persist(personalInformation);
            em.getTransaction().commit();
            return personalInformation;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("rollback");
            }
            throw new RuntimeException("Could not persist entity", e);
        } finally {
            em.close();
        }
    }


        private  boolean getPersonalInformationBoolean(long personalId) {
            em = emf.createEntityManager();
            try {
                TypedQuery<PersonalInformation> query = em.createQuery("SELECT p FROM PersonalInformation p WHERE p.personId = :id", PersonalInformation.class);
                query.setParameter("id", personalId);
                return !query.getResultList().isEmpty();
            } finally {
                em.close();
            }
        }
    private  PersonalInformation getPersonalInformation(long personalId) {
        em = emf.createEntityManager();
        try {

            TypedQuery<PersonalInformation> query = em.createQuery("SELECT p FROM PersonalInformation p WHERE p.personId = :id", PersonalInformation.class);
            query.setParameter("id", personalId);
            return query.getSingleResult();
            }
        catch (Exception e) {
            return null;
        }
        finally {
            em.close();
        }

    }


    @Override
    public PersonalInformation getById(long id) {
        em = emf.createEntityManager();
        try {
            return em.find(PersonalInformation.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Set<PersonalInformation> getAll() {
        em = emf.createEntityManager();
        try {
            TypedQuery<PersonalInformation> query = em.createQuery("SELECT p FROM PersonalInformation p", PersonalInformation.class);
            return new HashSet<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public PersonalInformation update(PersonalInformation personalInformation) {
        em = emf.createEntityManager();
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




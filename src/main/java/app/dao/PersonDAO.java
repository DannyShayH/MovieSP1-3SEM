package app.dao;

import app.entities.Movie;
import app.entities.Person;
import app.interfaces.IDAO;
import app.services.EntityManagerFactoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class PersonDAO implements IDAO<Person> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public PersonDAO() {
        this.emf = EntityManagerFactoryService.getEntityManagerFactory();
    }

    @Override
    public Person create(Person person) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Could not persist entity", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Person getById(long id) {
        em = emf.createEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Set<Person> getAll() {
        em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            return new HashSet<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public Person update(Person person) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person updated = em.merge(person);
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
    public Person delete(long personId) {
        try (EntityManager em = emf.createEntityManager()) {
            Person person = em.find(Person.class, personId);
            if (person == null)
                throw new EntityNotFoundException("No entity found with id: " + personId);
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
            return person;
        }
    }

}




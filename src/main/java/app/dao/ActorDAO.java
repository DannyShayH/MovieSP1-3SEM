package app.dao;

import app.dto.ActorDTO;
import app.entities.Actor;
import app.persistence.IDAO;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class ActorDAO implements IDAO<Actor> {
    private static EntityManagerFactory emf;


    @Override
    public Actor create(Actor actorDTO) {
        return null;
    }

    @Override
    public Actor getById(long id) {
        return null;
    }

    @Override
    public Actor update(Actor actorDTO) {
        return null;
    }

    @Override
    public Actor delete(long id) {
        return null;
    }

    @Override
    public Set<Actor> getAll() {
        return Set.of();
    }
}

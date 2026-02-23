package app.dao;

import app.entities.Director;
import app.persistence.IDAO;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class DirectorDAO implements IDAO<Director> {

    private static EntityManagerFactory emf;

    @Override
    public Director create(Director director) {
        return null;
    }

    @Override
    public Director getById(long id) {
        return null;
    }

    @Override
    public Director update(Director director) {
        return null;
    }

    @Override
    public Director delete(long id) {
        return null;
    }

    @Override
    public Set<Director> getAll() {
        return Set.of();
    }
}

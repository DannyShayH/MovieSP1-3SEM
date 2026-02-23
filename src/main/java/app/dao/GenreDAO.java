package app.dao;

import app.dto.GenreDTO;
import app.entities.Genre;
import app.persistence.IDAO;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class GenreDAO implements IDAO<Genre> {
    private static EntityManagerFactory emf;


    @Override
    public Genre create(Genre genreDTO) {
        return null;
    }

    @Override
    public Genre getById(long id) {
        return null;
    }

    @Override
    public Genre update(Genre genreDTO) {
        return null;
    }

    @Override
    public Genre delete(long id) {
        return null;
    }

    @Override
    public Set<Genre> getAll() {
        return Set.of();
    }
}

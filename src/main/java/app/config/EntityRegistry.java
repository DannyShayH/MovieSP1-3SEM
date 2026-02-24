package app.config;

import app.dto.CrewDTO;
import app.entities.*;
import org.hibernate.cfg.Configuration;

final class EntityRegistry {

    private EntityRegistry() {}

    static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(Actor.class);
        configuration.addAnnotatedClass(Director.class);
        configuration.addAnnotatedClass(Genre.class);
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(Crew.class);

    }
}
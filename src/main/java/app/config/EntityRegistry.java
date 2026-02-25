package app.config;

import app.entities.*;
import org.hibernate.cfg.Configuration;

final class EntityRegistry {

    private EntityRegistry() {}

    static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(ActorInMovie.class);
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Genre.class);
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(CrewInMovie.class);

    }
}
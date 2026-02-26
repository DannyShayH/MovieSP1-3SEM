package app.services;

import app.dto.ActorInMovieDTO;
import app.dto.CrewInMovieDTO;
import app.entities.Crew;
import app.entities.Actor;

import java.util.ArrayList;
import java.util.List;

public class ProductionService {


    public static List<Actor> actorConverter(List<ActorInMovieDTO> people) {
        List<Actor> actors = new ArrayList<>();
        for (ActorInMovieDTO person : people) {
                Actor tempActor = new Actor();
                tempActor.setActorId(person.getId());
                actors.add(tempActor);
        }
        if (actors != null) {
            return actors;
        }
        throw new RuntimeException("List of actors is null");
    }

    public static List<Crew> crewConverter(List<CrewInMovieDTO> people) {

        List<Crew> crew = new ArrayList<>();;

        for(CrewInMovieDTO crewInMovieDTO : people) {

            Crew tempcrew = new Crew();
            tempcrew.setCrewId(crewInMovieDTO.getId());
//            tempcrew.setDepartment(crewInMovieDTO.getDepartment());
//            tempcrew.setJob(crewInMovieDTO.getJob());
            crew.add(tempcrew);
        }
        return crew;
    }


}

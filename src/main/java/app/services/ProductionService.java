package app.services;

import app.dto.ActorDTO;
import app.dto.CrewDTO;
import app.entities.Crew;
import app.interfaces.IEntity;
import app.interfaces.IPerson;
import app.entities.Actor;

import java.util.ArrayList;
import java.util.List;

public class ProductionService {


    public static List<Actor> actorConverter(List<ActorDTO> people) {
        List<Actor> actors = new ArrayList<>();
        for (ActorDTO person : people) {
                Actor tempActor = new Actor();
                tempActor.setActorId(person.getId());
                tempActor.setName(person.getName());
                tempActor.setOriginalName(person.getOriginalName());
                tempActor.setCharacter(person.getCharacter());
                tempActor.setGender(person.getGender());
                actors.add(tempActor);
        }
        if (actors != null) {
            return actors;
        }
        throw new RuntimeException("List of actors is null");
    }

    public static List<Crew> crewConverter(List<CrewDTO> people) {

        List<Crew> crew = new ArrayList<>();;

        for(CrewDTO crewDTO : people) {

            Crew tempcrew = new Crew();
            tempcrew.setCrewId(crewDTO.getId());
            tempcrew.setName(crewDTO.getName());
            tempcrew.setDepartment(crewDTO.getDepartment());
            tempcrew.setJob(crewDTO.getJob());
            tempcrew.setGender(crewDTO.getGender());
            crew.add(tempcrew);
        }
        return crew;
    }


}

package app.services;

import app.dto.ActorInMovieDTO;
import app.dto.CrewInMovieDTO;
import app.entities.CrewInMovie;
import app.entities.ActorInMovie;

import java.util.ArrayList;
import java.util.List;

public class ProductionService {


    public static List<ActorInMovie> actorConverter(List<ActorInMovieDTO> people) {
        List<ActorInMovie> actorInMovies = new ArrayList<>();
        for (ActorInMovieDTO person : people) {
                ActorInMovie tempActorInMovie = new ActorInMovie();
                tempActorInMovie.setActorId(person.getId());
                tempActorInMovie.setOriginalName(person.getOriginalName());
                tempActorInMovie.setCharacter(person.getCharacter());
                actorInMovies.add(tempActorInMovie);
        }
        if (actorInMovies != null) {
            return actorInMovies;
        }
        throw new RuntimeException("List of actors is null");
    }

    public static List<CrewInMovie> crewConverter(List<CrewInMovieDTO> people) {

        List<CrewInMovie> crewInMovie = new ArrayList<>();;

        for(CrewInMovieDTO crewInMovieDTO : people) {

            CrewInMovie tempcrew = new CrewInMovie();
            tempcrew.setCrewId(crewInMovieDTO.getId());
            tempcrew.setDepartment(crewInMovieDTO.getDepartment());
            tempcrew.setJob(crewInMovieDTO.getJob());
            crewInMovie.add(tempcrew);
        }
        return crewInMovie;
    }


}

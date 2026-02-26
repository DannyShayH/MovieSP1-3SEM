package app.services;

import app.dto.ActorInMovieDTO;
import app.dto.CrewInMovieDTO;
import app.entities.Crew;
import app.entities.Actor;
import app.entities.MovieActor;
import app.entities.MovieCrew;

import java.util.ArrayList;
import java.util.List;

public class ProductionService {


    public static List<MovieActor> actorConverter(List<ActorInMovieDTO> people) {
        List<MovieActor> actors = new ArrayList<>();
        for (ActorInMovieDTO person : people) {
                MovieActor tempActor = new MovieActor();
                tempActor.setId(person.getId());
                actors.add(tempActor);
        }
        if (actors != null) {
            return actors;
        }
        throw new RuntimeException("List of actors is null");
    }

//    public static List<MovieCrew> crewConverter(List<CrewInMovieDTO> people) {
//
//        List<Crew> crew = new ArrayList<>();;
//
//        for(CrewInMovieDTO crewInMovieDTO : people) {
//
//            MovieCrew tempcrew = new Crew();
//            tempcrew.setCrewId(crewInMovieDTO.getId());
////            tempcrew.setDepartment(crewInMovieDTO.getDepartment());
////            tempcrew.setJob(crewInMovieDTO.getJob());
//            crew.add(tempcrew);
//        }
//        return crew;
//    }


}

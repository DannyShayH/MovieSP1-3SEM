package app.services;

import app.dao.PersonalInfoDAO;
import app.dto.*;
import app.entities.Actor;
import app.entities.Crew;
import app.entities.PersonalInformation;
import app.dto.ProductionDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonFactory {






        public static List<Actor> getActorsFromMovie(ProductionDTO productionDTO){
            List<ActorInMovieDTO> actorInMovieDTOS = productionDTO.getCast();
            List<Actor> actors = new ArrayList<>();
            Actor actor;
            for (ActorInMovieDTO actorInMovieDTO : actorInMovieDTOS){
                actor = new Actor();
                actor.setActorId(actorInMovieDTO.getId());
                actors.add(actor);
            }
            return actors;
        }

        public static Set<Crew> getCrewFromMovie(ProductionDTO productionDTO){
            List<CrewInMovieDTO> crewInMovieDTOS =productionDTO.getCrew();
            Set<Crew> crews = new HashSet<>();
            Crew crew;
            for (CrewInMovieDTO crewInMovieDTO : crewInMovieDTOS){
                crew = new Crew();
                crew.setCrewId(crewInMovieDTO.getId());
                crews.add(crew);

            }
            return crews;
        }




    public static PersonalInformation setAnActorsPersonalInformation(Actor actor){
     PersonalInformation personalInformation = new PersonalInformation();

            PersonDTO personDTO = PersonService.getPerson(String.valueOf(actor.getActorId()));
            personalInformation.setPersonId(personDTO.getId());
            personalInformation.setName(personDTO.getName());
            personalInformation.setKnownAs(personDTO.getAlsKnownAs());
            personalInformation.setBirthday(personDTO.getBirthday());
            personalInformation.setDeathday(personDTO.getDeathday());
            personalInformation.setBiography(personDTO.getBiography());
            personalInformation.setPopularity(personDTO.getPopularity());
            personalInformation.setGender(personDTO.getGender());
            personalInformation.setBirthPlace(personDTO.getPlaceOfBirth());
            personalInformation.setKnownForDepartment(personDTO.getDepartment());
            personalInformation.setActor(actor);



      return personalInformation;
    }
    public static PersonalInformation setACrewsPersonalInformation(Crew crew){
     PersonalInformation personalInformation = new PersonalInformation();

            PersonDTO personDTO = PersonService.getPerson(String.valueOf(crew.getCrewId()));
            personalInformation.setPersonId(personDTO.getId());
            personalInformation.setName(personDTO.getName());
            personalInformation.setKnownAs(personDTO.getAlsKnownAs());
            personalInformation.setBirthday(personDTO.getBirthday());
            personalInformation.setDeathday(personDTO.getDeathday());
            personalInformation.setBiography(personDTO.getBiography());
            personalInformation.setPopularity(personDTO.getPopularity());
            personalInformation.setGender(personDTO.getGender());
            personalInformation.setBirthPlace(personDTO.getPlaceOfBirth());
            personalInformation.setKnownForDepartment(personDTO.getDepartment());
            personalInformation.setCrew(crew);



      return personalInformation;
    }

    public static PersonalInformation getPersonById(long id){
        PersonalInformation personalInformation;
        PersonalInfoDAO personalInfoDAO = new PersonalInfoDAO();
        personalInformation = personalInfoDAO.getById(id);
        return personalInformation;


    }

}
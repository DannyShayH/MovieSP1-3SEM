package app.services.persistenceServices;

import app.dto.PersonDTO;
import app.entities.Actor;
import app.entities.Crew;
import app.entities.PersonalInformation;
import app.services.ApiServices.PersonService;

public class PersonFactory {


    public static PersonalInformation setAnActorsPersonalInformation(Actor actor)
    {

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

    public static PersonalInformation setACrewsPersonalInformation(Crew crew)
    {

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


}
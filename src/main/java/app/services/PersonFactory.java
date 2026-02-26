package app.services;

import app.dao.PersonDAO;
import app.dto.*;
import app.entities.PersonalInformation;
import app.dto.ProductionDTO;
import java.util.HashSet;
import java.util.Set;

public class PersonFactory {

        private static Set<String> TotalCastID = new HashSet<>();

        public static void addPeopleToList(ProductionDTO productionDTO){
            for (CrewInMovieDTO crewMember : productionDTO.getCrew() ){
                Long id =  crewMember.getId();
                TotalCastID.add(String.valueOf(id));
            }
            for (ActorInMovieDTO actor : productionDTO.getCast()){
                Long id = actor.getId();
                TotalCastID.add(String.valueOf(id));
            }
        }
    public static Set<PersonalInformation> getAllPeopleFromAllMovies(){
        Set<PersonalInformation> people = new HashSet<>();
        int x =0;
        for (String id : TotalCastID){
            PersonalInformation personalInformation = new PersonalInformation();
            System.out.println(x);
            PersonDTO personDTO = PersonService.getPerson(id);
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


            people.add(personalInformation);
            x++;
        }
      return people;
    }

    public static PersonalInformation getPersonById(long id){
        PersonalInformation personalInformation;
        PersonDAO personDAO = new PersonDAO();
        personalInformation = personDAO.getById(id);
        return personalInformation;


    }

}
package app.services;

import app.dto.*;
import app.entities.Person;
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
    public static Set<Person> getAllPeopleFromAllMovies(){
        Set<Person> people = new HashSet<>();
        int x =0;
        for (String id : TotalCastID){
            Person person = new Person();
            System.out.println(x);
            PersonDTO personDTO = PersonService.getPerson(id);
            person.setPersonId(personDTO.getId());
            person.setName(personDTO.getName());
            person.setKnownAs(personDTO.getAlsKnownAs());
            person.setBirthday(personDTO.getBirthday());
            person.setDeathday(personDTO.getDeathday());
            person.setBiography(personDTO.getBiography());
            person.setPopularity(personDTO.getPopularity());
            person.setGender(personDTO.getGender());
            person.setBirthPlace(personDTO.getPlaceOfBirth());
            person.setKnownForDepartment(personDTO.getDepartment());



            people.add(person);
            x++;
        }
      return people;
    }
}
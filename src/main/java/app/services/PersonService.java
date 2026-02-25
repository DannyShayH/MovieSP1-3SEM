package app.services;

import app.dto.PersonDTO;
import app.utils.ApiFetcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonService {
    static ObjectMapper objectMapper = ObjectMapperService.getMapper();
    static String apiKey = System.getenv("API_KEY");
    static String allActorsUrl = "https://api.themoviedb.org/3/person/€1?language=en-US&api_key=€2";


    public static PersonDTO getPerson(String actorId){
        String url =  allActorsUrl;
        url = url.replace("€1", actorId);
        url = url.replace("€2",apiKey);

        JsonNode response = ApiFetcher.getApiDataWithMapper(url, objectMapper);
        try{
            return objectMapper.treeToValue(response, PersonDTO.class);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

}
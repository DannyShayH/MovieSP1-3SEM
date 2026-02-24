package app.services;

import app.dto.ActorDTO;
import app.dto.CrewDTO;
import app.utils.ApiFetcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class CreditsService {
    static ObjectMapper objectMapper = ObjectMapperService.getMapper();
    static String url = "https://api.themoviedb.org/3/movie/movie_id/credits?language=da-DK&api_key=$1";
    static String apiKey = System.getenv("API_KEY");

    public static ActorDTO getActor(){
        List<ActorDTO> actors = null;
        url = url.replace("$1", apiKey);
        JsonNode response = ApiFetcher.getApiDataWithMapper(url, objectMapper);

        try {
            return objectMapper.treeToValue(response.get("cast").get(0), ActorDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static CrewDTO getDirector(){
        List<CrewDTO> directors = null;
        url = url.replace("$1", apiKey);
        JsonNode response = ApiFetcher.getApiDataWithMapper(url, objectMapper);
        try {
            return objectMapper.treeToValue(response.get("crew").get(0), CrewDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

package app.services;

import app.dto.MovieDTO;
import app.utils.ApiFetcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class MovieService {
    static ObjectMapper objectMapper = ObjectMapperService.getMapper();
    static String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=da-DK&page=*&primary_release_year=$&sort_by=popularity.desc&with_origin_country=DK&api_key=$1";
    static String apiKey = "82069456ff0f378d0ddb400bc8419f80";
    static String singleUrl = "https://api.themoviedb.org/3/search/movie?query=bastarden&include_adult=false&language=en-US&page=1&api_key=$1";

    public MovieService(EntityManagerFactory emf) {
    }

    public static MovieDTO getMovie() {

        singleUrl= singleUrl.replace("$1", apiKey);
        JsonNode response = ApiFetcher.getApiDataWithMapper(singleUrl, objectMapper);
        try {
            return objectMapper.treeToValue(response.get("results").get(0), MovieDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    public List<MovieDTO> getDanishMovies() {
        List<MovieDTO> movies = null;
        url = url.replace("$1", apiKey);
        int page = 1;
        int maxPage = 1;
        int totalResult = 0;
        List<String> ids = new ArrayList<>();
        String[] years = {"2021", "2022", "2023", "2024", "2025", "2026"};
        for (int i = 0; i < years.length; i++) {
            url = url.replace("$", (years[i]));
            for (int p = 0; p <= maxPage; p++) {
                url = url.replace("*", String.valueOf(page));
                JsonNode response = ApiFetcher.getApiDataWithMapper(url, objectMapper);
                System.out.println(response.toPrettyString());
                maxPage = response.get("total_pages").asInt();
                page++;
                ids.add(response.get("results").get(0).get("id").asText());
                totalResult += response.get("total_results").asInt();
            }
            page = 1;
            maxPage = 1;
        }
        System.out.println(totalResult);


        return movies;
    }

}

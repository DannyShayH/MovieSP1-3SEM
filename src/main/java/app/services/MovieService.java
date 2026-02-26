package app.services;

import app.dto.MovieDTO;
import app.dto.ProductionDTO;
import app.utils.ApiFetcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieService {
    static ObjectMapper objectMapper = ObjectMapperService.getMapper();
    static String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=da-DK&page=$2&primary_release_year=$3&sort_by=popularity.desc&with_origin_country=DK&api_key=$1";
    static String apiKey = System.getenv("API_KEY");
   // static String singleUrl = "https://api.themoviedb.org/3/search/movie?query=bastarden&include_adult=false&language=en-US&page=1&api_key=$1";
    static String productionUrl = "https://api.themoviedb.org/3/movie/€1/credits?language=en-US&api_key=€2";
//    public static MovieDTO getMovie() {

    /*   singleUrl= singleUrl.replace("$1", apiKey);
        JsonNode response = ApiFetcher.getApiDataWithMapper(singleUrl, objectMapper);
        try {
            return objectMapper.treeToValue(response.get("results").get(0), MovieDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
    */

    public static List<MovieDTO> getDanishMovies() {
        List<MovieDTO> movies = new ArrayList<>();
        url = url.replace("$1", apiKey);
        int page = 1;
        int maxPage = 1;
        int totalResult = 0;
//        List<String> ids = new ArrayList<>();
        List<List<MovieDTO>> tempDTO = new ArrayList<>();
        String[] years = {"2021",/* "2022", "2023", "2024", "2025", */"2026"};
        for (int i = 0; i < years.length; i++) {
            String urlPlaceholderYear = url;
            url = url.replace("$3", (years[i]));
            for (int p = 0; p <= maxPage; p++) {
                String urlPlaceholder = url;
                url = url.replace("$2", String.valueOf(page));
                JsonNode response = ApiFetcher.getApiDataWithMapper(url, objectMapper);
                try {
                    tempDTO.add(objectMapper.readValue(
                            response.get("results").traverse(),
                            new com.fasterxml.jackson.core.type.TypeReference<List<MovieDTO>>() {

                            }
                    ));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(response.toPrettyString());
                maxPage = response.get("total_pages").asInt();

//                ids.add(response.get("results").get(0).get("id").asText());
                totalResult += response.get("total_results").asInt();
                url = urlPlaceholder;
                page++;

            }
            page = 1;
            maxPage = 1;
            url = urlPlaceholderYear;
        }
        System.out.println(totalResult);

        for(List<MovieDTO> movieDTOS : tempDTO){
            for(MovieDTO movieDTO : movieDTOS){
               if(!movies.contains(movieDTO)){
                   movies.add(movieDTO);
               }
            }
        }


        return movies;
    }

    public static ProductionDTO getProductionTeam(String movieId) {
        ProductionDTO productionDTO = null;
        url = productionUrl;
        url = url.replace("€1", movieId);
        url = url.replace("€2", apiKey);
        JsonNode response = ApiFetcher.getApiDataWithMapper(url, objectMapper);
        try {
            return objectMapper.treeToValue(response, ProductionDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

package app.services;

import app.dto.GenreDTO;
import app.entities.Genre;
import app.utils.ApiFetcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

public class GenreApiService {
    static String url = "https://api.themoviedb.org/3/genre/movie/list?language=en&api_key=$1";
    static String apiKey = System.getenv("API_KEY");
    static ObjectMapper objectMapper = ObjectMapperService.getMapper();

    public GenreApiService(){
        getAllGenres();
    }

    public static GenreDTO[] getGenre() {

        url = url.replace("$1", apiKey);
        JsonNode response = ApiFetcher.getApiDataWithMapper(url, objectMapper);
        try {
            return objectMapper.treeToValue(response.get("genres"), GenreDTO[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static Set<Genre> getAllGenres(){
        Set<Genre> genres = new HashSet<>();
        for(GenreDTO genreDTO : getGenre()){
            long id = genreDTO.getId();
            if(genres.stream().anyMatch(genre -> genre.getId() != id)){

                genres.add(new Genre(genreDTO.getId(),genreDTO.getName()));
            }
        }
            return genres;
    }

    public Genre getGenreById(long id){

        Genre genre = null;
        for(GenreDTO genreDTO : getGenre()){
            if(genreDTO.getId() == id){
                genre = new Genre(genreDTO.getId(),genreDTO.getName());
            }
        }
        return genre;
    }
}

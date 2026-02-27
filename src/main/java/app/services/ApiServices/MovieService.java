package app.services.ApiServices;

import app.dto.MovieDTO;
import app.dto.ProductionDTO;
import app.utils.ApiFetcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MovieService {
    private  ObjectMapper objectMapper = ObjectMapperService.getMapper();
    private  String discoverUrlTemplate = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=da-DK&page=%d&primary_release_year=%s&sort_by=popularity.desc&with_origin_country=DK&api_key=%s";
    private static String apiKey = System.getenv("API_KEY");
    private static String productionUrl = "https://api.themoviedb.org/3/movie/€1/credits?language=en-US&api_key=€2";


    public List<MovieDTO> getDanishMovies()
    {
        List<MovieDTO> movies = new ArrayList<>();
        String[] years = {"2021", "2022", "2023", "2024", "2025", "2026"};
        int maxParallel = 4;

        for (String year : years)
        {
            JsonNode firstPage = ApiFetcher.getApiDataWithMapper(buildDiscoverUrl(year, 1), objectMapper);
            movies.addAll(parseMovies(firstPage));
            int totalPages = firstPage.get("total_pages").asInt();

            if (totalPages <= 1)
            {
                continue;
            }

            ExecutorService executor = Executors.newFixedThreadPool(maxParallel);
            List<Future<List<MovieDTO>>> futures = new ArrayList<>();
            for (int page = 2; page <= totalPages; page++)
            {
                int currentPage = page;
                futures.add(executor.submit(new Callable<List<MovieDTO>>()
                {
                    @Override
                    public List<MovieDTO> call()
                    {
                        JsonNode response = ApiFetcher.getApiDataWithMapper(
                                buildDiscoverUrl(year, currentPage), objectMapper);
                        return parseMovies(response);
                    }
                }));
            }

            for (Future<List<MovieDTO>> future : futures)
            {
                try {
                    movies.addAll(future.get());
                } catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                } catch (ExecutionException e)
                {
                    throw new RuntimeException(e);
                }
            }
            executor.shutdown();
        }

        return dedupeByMovieId(movies);
    }

    public ProductionDTO getProductionTeam(String movieId)
    {
        String url = productionUrl;
        url = url.replace("€1", movieId);
        url = url.replace("€2", apiKey);
        JsonNode response = ApiFetcher.getApiDataWithMapper(url, objectMapper);
        try {
            return objectMapper.treeToValue(response, ProductionDTO.class);
        } catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    private String buildDiscoverUrl(String year, int page)
    {
        if (apiKey == null || apiKey.isBlank())
        {
            throw new RuntimeException("API_KEY environment variable is not set");
        }
        return String.format(discoverUrlTemplate, page, year, apiKey);
    }

    private  List<MovieDTO> parseMovies(JsonNode response)
    {
        try {
            return objectMapper.readValue(
                    response.get("results").traverse(),
                    new com.fasterxml.jackson.core.type.TypeReference<List<MovieDTO>>() {}
            );

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static List<MovieDTO> dedupeByMovieId(List<MovieDTO> movies)
    {
        List<MovieDTO> unique = new ArrayList<>();
        Set<Long> seen = new HashSet<>();
        for (MovieDTO movie : movies)
        {
            if (seen.add(movie.getId()))
            {
                unique.add(movie);
            }
        }
        return unique;
    }

}

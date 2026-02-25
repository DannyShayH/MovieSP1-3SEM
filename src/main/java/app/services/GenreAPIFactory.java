package app.services;

import lombok.Getter;

public class GenreAPIFactory {
    @Getter
    private static final GenreApiService  genreApiService = new GenreApiService();
}

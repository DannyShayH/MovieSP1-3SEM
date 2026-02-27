package app;

import app.dao.MovieDAO;

import app.services.ApiServices.MovieSyncService;
import app.services.persistenceServices.EntityManagerFactoryService;


public class App {
    public static void initiate()
    {


//        MovieSyncService.syncFromTmdb();

        MovieDAO movieDAO = new MovieDAO(EntityManagerFactoryService.getEntityManagerFactory());
        movieDAO.getAll().forEach(System.out::println);
        movieDAO.getTop10LowestRated().forEach(System.out::println);
        movieDAO.getTop10MostPopular().forEach(System.out::println);
        movieDAO.getTop10HighestRated().forEach(System.out::println);
        movieDAO.getMoviesByActorId(1).forEach(System.out::println);
        movieDAO.getMoviesByActorId(1019).forEach(System.out::println);
        movieDAO.getMoviesByDirectorId(14).forEach(System.out::println);
        movieDAO.getAverageRating();
        movieDAO.updateTitleAndReleaseDate(778819,"Ninjaen der er ternet","2021-09-16");
        movieDAO.searchByTitle("Ninjaen ").forEach(System.out::println);


    }


}

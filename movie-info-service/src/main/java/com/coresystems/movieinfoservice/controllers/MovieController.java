package com.coresystems.movieinfoservice.controllers;

import com.coresystems.movieinfoservice.models.Movie;
import com.coresystems.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    List<MovieSummary> movieSummaryList = Arrays.asList(
            new MovieSummary("1", "The Punisher", "Action Movie released in 2006"),
            new MovieSummary("2", "The Naked Pie", "Comedy Movie released in 2006"),
            new MovieSummary("3", "Friends with benefits", "Romance Movie released in 2006")
    );

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId){
        List<Movie> movie = movieSummaryList.stream()
                .filter(movieSummary -> movieSummary.getId().equalsIgnoreCase(movieId))
                .map(movieSummary -> new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview()))
                .collect(Collectors.toList());
        return movie.get(0);
    }
}

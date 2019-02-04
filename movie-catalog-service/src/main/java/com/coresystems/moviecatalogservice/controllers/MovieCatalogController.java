package com.coresystems.moviecatalogservice.controllers;

import com.coresystems.moviecatalogservice.models.CatalogItem;
import com.coresystems.moviecatalogservice.models.Movie;
import com.coresystems.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("1", 4),
                new Rating("2", 3)
        );

        return ratings.stream().map(rating -> {
            //getting data from api endpoint and unmarshalling it into a local object
            Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie != null ? movie.getName() : "no movie found", "Kevin Hart in this movie shows us how school is important", rating.getRating());
        }).collect(Collectors.toList());

//        return Collections.singletonList(new CatalogItem("Night School", "Kevin Hart in this movie shows us how school is important", 5));
    }
}

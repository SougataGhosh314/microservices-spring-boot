package com.sougata.source.resources;

import com.sougata.source.models.CatalogItem;
import com.sougata.source.models.Movie;
import com.sougata.source.models.Rating;
import com.sougata.source.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    public MovieCatalogResource(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        UserRating ratings =
                restTemplate.getForObject("http://localhost:8082/movies/" + userId,
                UserRating.class);

        assert ratings != null;
        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(),
                    Movie.class);

//            Movie movie = webClientBuilder.build()
//                    .get().uri("http://localhost:8082/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();

            if (movie != null) {
                return new CatalogItem(movie.getName(), "Desc", rating.getRating());
            } else {
                return null;
            }
        }).collect(Collectors.toList());

    }
}

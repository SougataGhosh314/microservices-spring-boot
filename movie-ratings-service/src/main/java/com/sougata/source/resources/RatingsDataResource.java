package com.sougata.source.resources;

import com.sougata.source.models.Rating;
import com.sougata.source.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable String userId) {
        return new UserRating(
                Arrays.asList(
                        new Rating("1234", 4),
                        new Rating("5678", 5)
        ));
    }
}

package com.example.fluxflixservice.service;

import com.example.fluxflixservice.model.Movie;
import com.example.fluxflixservice.model.MovieEvent;
import com.example.fluxflixservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class FluxFlixService {

    private final MovieRepository movieRepository;

    @Autowired
    public FluxFlixService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Flux<MovieEvent> streamStreams(Movie movie) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<MovieEvent> events = Flux.fromStream(Stream.generate(() -> new MovieEvent(movie, new Date(), randomUser())));

        return Flux.zip(interval, events)
                .map(Tuple2::getT2);
    }

    private String randomUser() {
        String[] users = "josh,mkheck,jack,phil,star".split(",");

        return users[new Random().nextInt(users.length)];
    }

    public Flux<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Mono<Movie> findById(String id) {
        return movieRepository.findById(id);
    }
}

package com.example.fluxflixservice.controller;

import com.example.fluxflixservice.model.Movie;
import com.example.fluxflixservice.model.MovieEvent;
import com.example.fluxflixservice.service.FluxFlixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RestController
public class FluxFlixController {
    private final FluxFlixService fluxFlixService;

    @Autowired
    public FluxFlixController(FluxFlixService fluxFlixService) {
        this.fluxFlixService = fluxFlixService;
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieEvent> events(@PathVariable String id) {
        return fluxFlixService.findById(id).flatMapMany(fluxFlixService::streamStreams);
    }

    @GetMapping
    public Flux<Movie> all() {
        return fluxFlixService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Movie> byId(@PathVariable String id) {
        return fluxFlixService.findById(id);
    }
}

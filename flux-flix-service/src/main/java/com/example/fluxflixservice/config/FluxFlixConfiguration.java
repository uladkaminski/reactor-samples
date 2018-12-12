package com.example.fluxflixservice.config;

import com.example.fluxflixservice.model.Movie;
import com.example.fluxflixservice.model.MovieEvent;
import com.example.fluxflixservice.service.FluxFlixService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class FluxFlixConfiguration {

    @Bean
    RouterFunction<?> routers(FluxFlixService fluxFlixService) {
        return route(GET("/movies"), request -> ok().body(fluxFlixService.findAll(), Movie.class)
        ).andRoute(GET("/movies/{id}"), request -> ok().body(fluxFlixService.findById(request.pathVariable("id")), Movie.class))
                .andRoute(GET("/movies/{id}/events"), request -> ok().contentType(MediaType.TEXT_EVENT_STREAM).body(fluxFlixService.findById(request.pathVariable("id")).flatMapMany(fluxFlixService::streamStreams), MovieEvent.class));
    }
}

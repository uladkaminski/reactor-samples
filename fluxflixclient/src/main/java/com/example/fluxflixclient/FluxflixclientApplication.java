package com.example.fluxflixclient;

import com.example.fluxflixclient.model.Movie;
import com.example.fluxflixclient.model.MovieEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class FluxflixclientApplication {


    public static void main(String[] args) {
        SpringApplication.run(FluxflixclientApplication.class, args);
    }

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080/movies");
    }

    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get().uri("").exchange()
                    .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Movie.class))
                    .filter(movie -> movie.getTitle().toLowerCase().contains("The Fluxinator".toLowerCase()))
                    .subscribe(movie -> client.get()
                            .uri("/{id}/events", movie.getId())
                            .exchange()
                            .flatMapMany(cr -> cr.bodyToFlux(MovieEvent.class))
                            .subscribe(System.out::println)
                    );
        };
    }

}


package com.example.fluxflixservice;

import com.example.fluxflixservice.model.Movie;
import com.example.fluxflixservice.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@EnableMongoRepositories
@SpringBootApplication
public class FluxFlixServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluxFlixServiceApplication.class, args);
    }




    @Bean
    CommandLineRunner demo(MovieRepository movieRepository) {
        return args -> movieRepository.deleteAll().subscribe(null, null, () -> Stream.of("Aeon Flux", "Enter the Mono", "The Fluxinator", "Reactive", "Attack of tje fluxes", "Mono in the town silence", "Back to the future")
                .map(name -> new Movie(UUID.randomUUID().toString(), name, randomGenre()))
                .forEach(movie -> movieRepository.save(movie).subscribe(System.out::println)));

    }

    private String randomGenre() {
        String[] genres = "horror,romcom,drama,action,documentary".split(",");
        return genres[new Random().nextInt(genres.length)];
    }

}


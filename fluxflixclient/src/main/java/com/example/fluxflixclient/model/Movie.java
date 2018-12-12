package com.example.fluxflixclient.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
@EqualsAndHashCode
public class Movie {

    private String id;
    private String title;
    private String genre;


}

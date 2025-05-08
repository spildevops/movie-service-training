package com.johanwork.mapper;

import com.johanwork.dto.MovieDto;
import com.johanwork.entity.Movie;

public class EntityDtoMapper {

    public static MovieDto toDto(Movie movie){
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getGenre()
        );
    }

}

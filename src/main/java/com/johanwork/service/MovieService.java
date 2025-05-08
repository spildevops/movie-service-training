package com.johanwork.service;

import com.johanwork.domain.Genre;
import com.johanwork.dto.MovieDto;
import com.johanwork.entity.Movie;
import com.johanwork.mapper.EntityDtoMapper;
import com.johanwork.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieDto> getAll(){
        return movieRepository.findAll()
                .stream()
                .map(EntityDtoMapper::toDto)
                .toList();
    }

    public List<MovieDto> getByGenre(Genre genre){
        return movieRepository.findByGenre(genre)
                .stream()
                .map(EntityDtoMapper::toDto)
                .toList();
    }

}

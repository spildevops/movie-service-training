package com.johanwork.controller;

import com.johanwork.domain.Genre;
import com.johanwork.dto.MovieDto;
import com.johanwork.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {


    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getAll(){
        return this.movieService.getAll();
    }

    @GetMapping("/{genre}")
    public List<MovieDto> getAllGenre(@PathVariable Genre genre){
        return this.movieService.getByGenre(genre);
    }
}

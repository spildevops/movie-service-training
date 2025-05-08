package com.johanwork.dto;

import com.johanwork.domain.Genre;

public record MovieDto(Long id,
                       String title,
                       Integer releaseYear,
                       Genre genre) {
}

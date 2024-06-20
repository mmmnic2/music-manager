package com.firstversion.musicmanager.service;

import com.firstversion.musicmanager.dto.request.CreateGenreRequest;
import com.firstversion.musicmanager.dto.response.GenreResponse;
import com.firstversion.musicmanager.model.entity.Genre;

import java.util.List;

public interface GenreService {
    GenreResponse createGenre(CreateGenreRequest request);
    boolean deleteGenre(List<Long> genreIds);
    List<GenreResponse> getAllGenres();
}

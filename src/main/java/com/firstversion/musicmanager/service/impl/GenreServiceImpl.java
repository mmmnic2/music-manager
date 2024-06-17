package com.firstversion.musicmanager.service.impl;

import com.firstversion.musicmanager.dto.response.GenreResponse;
import com.firstversion.musicmanager.exception.AlreadyExistException;
import com.firstversion.musicmanager.exception.NotFoundException;
import com.firstversion.musicmanager.model.entity.Genre;
import com.firstversion.musicmanager.repository.GenreRepository;
import com.firstversion.musicmanager.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    GenreRepository genreRepository;

    @Override
    public GenreResponse createGenre(GenreResponse genreResponse) {
        if (genreResponse.getGenreName() == null || genreResponse.getGenreName().trim().isEmpty()) {
            throw new RuntimeException("Genre name is not valid.");
        }
        if (genreRepository.checkExistByName(genreResponse.getGenreName()) != null) {
            throw new AlreadyExistException("Genre has already existed.");
        }
        Genre genre = new Genre();
        genre.setGenreName(genreResponse.getGenreName());
        Genre savedGenre = genreRepository.save(genre);
        return savedGenre.toGenreResponse();
    }

    @Override
    public boolean deleteGenre(List<Long> genreIds) {
        List<Genre> genres = genreRepository.findAllById(genreIds);
        if (genres.isEmpty()) throw new NotFoundException("Genre not found.");
        for (Genre genre : genres) {
            genreRepository.delete(genre);
        }
        return true;
    }

    @Override
    public List<GenreResponse> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        if (genres.isEmpty()) return List.of();
        return genres.stream().map(Genre::toGenreResponse).toList();
    }
}

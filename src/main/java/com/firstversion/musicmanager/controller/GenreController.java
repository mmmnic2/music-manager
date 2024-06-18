package com.firstversion.musicmanager.controller;

import com.firstversion.musicmanager.dto.request.CreateGenreRequest;
import com.firstversion.musicmanager.dto.response.AuthorResponse;
import com.firstversion.musicmanager.dto.response.GenreResponse;
import com.firstversion.musicmanager.dto.response.ResponseObject;
import com.firstversion.musicmanager.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {
    @Autowired
    GenreService genreService;

    @PostMapping("/create-new-genre")
    public ResponseEntity<?> createGenre(@RequestBody CreateGenreRequest request) {
        GenreResponse response = genreService.createGenre(request);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Unable to create new genre", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.value(), "Create new genre successful", response)
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteGenre(@RequestBody List<Long> genreIds) {
        boolean isDeleted = genreService.deleteGenre(genreIds);
        if (isDeleted) {
            return ResponseEntity.ok(
                    new ResponseObject(HttpStatus.OK.value(), "Delete genre list successful.", null)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Unable to delete genre list.", null)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllAuthors() {
        List<GenreResponse> responses = genreService.getAllGenres();
        return ResponseEntity.ok(
                new ResponseObject(HttpStatus.OK.value(), "Get all genres successful.", responses)
        );
    }
}

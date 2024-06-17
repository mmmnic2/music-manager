package com.firstversion.musicmanager.controller;

import com.firstversion.musicmanager.dto.response.AuthorResponse;
import com.firstversion.musicmanager.dto.response.ResponseObject;
import com.firstversion.musicmanager.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/author")
@RestController
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/create-new-author")
    public ResponseEntity<ResponseObject> createAuthor(@RequestBody AuthorResponse authorResponse) {
        AuthorResponse response = authorService.createAuthor(authorResponse);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Unable to create new author", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.value(), "Create new author successful.", response)
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAuthorList(@RequestBody List<Long> authorIds) {
        boolean isDeleted = authorService.deleteAuthor(authorIds);
        if (isDeleted) {
            return ResponseEntity.ok(
                    new ResponseObject(HttpStatus.OK.value(), "Delete list author successful.", null)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Unable to delete list author.", null)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllAuthors() {
        List<AuthorResponse> responses = authorService.getAllAuthors();
        return ResponseEntity.ok(
                new ResponseObject(HttpStatus.OK.value(), "Get all authors successful.", responses)
        );
    }
}

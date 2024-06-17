package com.firstversion.musicmanager.controller;

import com.firstversion.musicmanager.dto.request.SongRequest;
import com.firstversion.musicmanager.dto.response.ResponseObject;
import com.firstversion.musicmanager.dto.response.SongResponse;
import com.firstversion.musicmanager.model.entity.Song;
import com.firstversion.musicmanager.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/songs")
public class SongController {
    @Autowired
    SongService songService;

    @GetMapping
    public ResponseEntity<ResponseObject> getSongs(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @RequestParam(required = false) Long genreId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SongResponse> responses;
        if (genreId != null) {
            responses = songService.getSongsByGenre(genreId, pageable);
        } else responses = songService.getSongs(pageable);
        return ResponseEntity.ok(
                new ResponseObject(HttpStatus.OK.value(), "Get all songs successful.", responses)
        );
    }

    @GetMapping("/create-new-song")
    public ResponseEntity<ResponseObject> createSong(@RequestBody SongRequest songRequest) throws IOException {
        SongResponse response = songService.saveSong(songRequest);
        return ResponseEntity.ok(
                new ResponseObject(HttpStatus.OK.value(), "Create new song successful.", response)
        );
    }

    @GetMapping("update-song")
    public ResponseEntity<ResponseObject> updateSong(@RequestBody SongRequest songRequest) throws IOException {
        SongResponse response = songService.saveSong(songRequest);
        return ResponseEntity.ok(
                new ResponseObject(HttpStatus.OK.value(), "Update song successful.", response)
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseObject> deleteSongList(@RequestBody List<Long> songIds) {
        boolean isDeleted = songService.deleteSongs(songIds);
        if (isDeleted == true)
            return ResponseEntity.ok(
                    new ResponseObject(HttpStatus.OK.value(), "Delete songIds list successful", new ResponseObject())
            );
        else
            return ResponseEntity.ok(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unable to delete songIds list.", null)
            );
    }

}
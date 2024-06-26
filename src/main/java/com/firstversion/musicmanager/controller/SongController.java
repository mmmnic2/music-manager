package com.firstversion.musicmanager.controller;

import com.firstversion.musicmanager.dto.request.SongRequest;
import com.firstversion.musicmanager.dto.request.UpdateSongRequest;
import com.firstversion.musicmanager.dto.response.ResponseObject;
import com.firstversion.musicmanager.dto.response.SongResponse;
import com.firstversion.musicmanager.model.entity.Song;
import com.firstversion.musicmanager.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        } else responses = songService.getAllSongs(pageable);
        return ResponseEntity.ok(
                new ResponseObject(HttpStatus.OK.value(), "Get all songs successful.", responses)
        );
    }

    @PostMapping(value = "/create-new-song", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> createSong(@RequestParam("songName") String songName,
                                                     @RequestParam("genreId") Long genreId,
                                                     @RequestPart(value = "image", required = false) MultipartFile image,
                                                     @RequestPart("src") MultipartFile src) throws IOException {
        SongRequest request = new SongRequest();
        request.setSongName(songName);
        request.setGenreId(genreId);
        request.setImage(image);
        request.setSrc(src);
        SongResponse response = songService.createSong(request);
        return ResponseEntity.ok(
                new ResponseObject(HttpStatus.OK.value(), "Create new song successful.", response)
        );
    }

    @PostMapping("/update-song/{songId}")
    public ResponseEntity<ResponseObject> updateSong(@PathVariable Long songId,
                                                     @RequestBody UpdateSongRequest songRequest) {
        SongResponse response = songService.updateSong(songId, songRequest);
        if (response != null) {
            return ResponseEntity.ok(
                    new ResponseObject(HttpStatus.OK.value(), "Update song successful.", response)
            );
        } else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unable to update song.", null)
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unable to delete songIds list.", null)
            );
    }

    @GetMapping("/{songId}")
    public ResponseEntity<ResponseObject> getSongById(@PathVariable Long songId) {
        SongResponse response = songService.getSongById(songId);
        if (response != null) {
            return ResponseEntity.ok(
                    new ResponseObject(HttpStatus.OK.value(), "Get song successful.", response)
            );
        } else
            return ResponseEntity.ok(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unable to delete songIds list.", null)
            );
    }

}

package com.firstversion.musicmanager.service;

import com.firstversion.musicmanager.dto.request.SongRequest;
import com.firstversion.musicmanager.dto.request.UpdateSongRequest;
import com.firstversion.musicmanager.dto.response.SongResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SongService {
    SongResponse createSong(SongRequest request) throws IOException;

    String uploadImage(MultipartFile image) throws IOException;

    String uploadSrc(MultipartFile src) throws IOException;

    SongResponse updateSong(Long songId, UpdateSongRequest request);

    SongResponse getSongById(Long songId);

    boolean deleteSongs(List<Long> songIds);

    Page<SongResponse> getAllSongs(Pageable pageable);

    Page<SongResponse> getSongsByGenre(Long genreId, Pageable pageable);
}

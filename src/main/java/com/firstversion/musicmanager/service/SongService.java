package com.firstversion.musicmanager.service;

import com.firstversion.musicmanager.dto.request.SongRequest;
import com.firstversion.musicmanager.dto.response.SongResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface SongService {
    SongResponse createSong(SongRequest request) throws IOException;

    SongResponse saveSong(SongRequest request) throws IOException;

    List<SongResponse> getAllSongs();

    SongResponse getSongById(Long songId);

    boolean deleteSongs(List<Long> songIds);

    Page<SongResponse> getSongs(Pageable pageable);

    Page<SongResponse> getSongsByGenre(Long genreId, Pageable pageable);
}

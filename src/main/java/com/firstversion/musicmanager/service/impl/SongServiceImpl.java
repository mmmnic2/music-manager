package com.firstversion.musicmanager.service.impl;

import com.cloudinary.Cloudinary;
import com.firstversion.musicmanager.config.CloudinaryService;
import com.firstversion.musicmanager.dto.request.SongRequest;
import com.firstversion.musicmanager.dto.response.SongResponse;
import com.firstversion.musicmanager.exception.NotFoundException;
import com.firstversion.musicmanager.model.entity.Author;
import com.firstversion.musicmanager.model.entity.Genre;
import com.firstversion.musicmanager.model.entity.Song;
import com.firstversion.musicmanager.repository.AuthorRepository;
import com.firstversion.musicmanager.repository.GenreRepository;
import com.firstversion.musicmanager.repository.SongRepository;
import com.firstversion.musicmanager.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    SongRepository songRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    CloudinaryService cloudinaryService;


    @Override
    public SongResponse createSong(SongRequest request) throws IOException {
        Author foundAuthor = authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new NotFoundException("Author not found."));
        Genre foundGenre = genreRepository.findById(request.getGenreId()).orElseThrow(() -> new NotFoundException("Genre not found."));
        String imageUrl = null;
        if (request.getImage() != null) {
            Map<String, String> result = cloudinaryService.uploadImage(request.getImage());
            imageUrl = result.get("url");
        }
        String videoUrl = null;
        if (request.getSrc() != null) {
            Map<String, String> result = cloudinaryService.uploadVideo(request.getSrc());
            videoUrl = result.get("url");
        }
        Song song = new Song();
        song.setSongName(request.getSongName());
        song.setAuthor(foundAuthor);
        song.setGenre(foundGenre);
        if (imageUrl != null) song.setImage(imageUrl);
        if (videoUrl != null) song.setSrc(videoUrl);
        Song savedSong = songRepository.save(song);
        return savedSong.toSongResponse();
    }

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        String imageUrl = null;
        if (image != null) {
            Map<String, Objects> result = cloudinaryService.uploadImage(image);
            imageUrl = result.get("url").toString();
        }
        return imageUrl;
    }

    @Override
    public String uploadSrc(MultipartFile src) throws IOException {
        String videoUrl = null;
        if (src != null) {
            Map<String, Objects> result = cloudinaryService.uploadVideo(src);
            videoUrl = result.get("url").toString();
        }
        return videoUrl;
    }

    @Override
    public SongResponse saveSong(SongRequest request) throws IOException {
//        Author foundAuthor = authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new NotFoundException("Author not found."));
//        Genre foundGenre = genreRepository.findById(request.getGenreId()).orElseThrow(() -> new NotFoundException("Genre not found."));
//        Song song = request.getSongId() != null ?
//                songRepository.findById(request.getSongId()).orElseThrow(() -> new NotFoundException("Song not found."))
//                : new Song();
//        String imageUrl = null;
//        if (request.getImage() != null) {
//            Map<String, Objects> result = cloudinaryService.uploadImage(request.getImage());
//            imageUrl = result.get("url").toString();
//        }
//        String videoUrl = null;
//        if (request.getImage() != null) {
//            Map<String, Objects> result = cloudinaryService.uploadVideo(request.getSrc());
//            videoUrl = result.get("url").toString();
//        }
//        if (request.getSongName() == null && request.getSongId() == null) {
//            throw new IllegalArgumentException("Song name cannot be null.");
//        }
//        song.setSongName(request.getSongName() != null ? request.getSongName() : song.getSongName());
//        song.setAuthor(foundAuthor);
//        song.setGenre(foundGenre);
//        if (imageUrl != null) song.setImage(imageUrl);
//        if (videoUrl != null) song.setSrc(videoUrl);
//        Song savedSong = songRepository.save(song);
//        return savedSong.toSongResponse();
        return null;
    }

    @Override
    public List<SongResponse> getAllSongs() {
        return List.of();
    }

    @Override
    public SongResponse getSongById(Long songId) {
        Song foundSong = songRepository.findById(songId).orElseThrow(() -> new NotFoundException("Song not found."));
        return foundSong.toSongResponse();
    }

    @Override
    public boolean deleteSongs(List<Long> songIds) {
        if (songIds == null || songIds.isEmpty()) {
            throw new IllegalArgumentException("The songIds list must not be null or empty.");
        }
        List<Song> songList = songRepository.findAllById(songIds);
        if (songList.isEmpty()) throw new NotFoundException("Song not found.");
        songRepository.deleteAll(songList);
        return true;
    }

    //get song with pagination
    @Override
    public Page<SongResponse> getSongs(Pageable pageable) {
        return songRepository.findAll(pageable).map(Song::toSongResponse);
//        return convertToSongResponse(songRepository.findAll(pageable));
    }

    //get song by genre with pagination
    @Override
    public Page<SongResponse> getSongsByGenre(Long genreId, Pageable pageable) {
        return songRepository.findByGenre(genreId, pageable).map(Song::toSongResponse);
    }

    private Page<SongResponse> convertToSongResponse(Page<Song> songPage) {
        List<SongResponse> songResponseList = songPage.getContent().stream().map(Song::toSongResponse).toList();
        return new PageImpl<>(songResponseList, songPage.getPageable(), songPage.getTotalElements());
    }
}

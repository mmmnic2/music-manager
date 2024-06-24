package com.firstversion.musicmanager.repository;

import com.firstversion.musicmanager.model.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    @Override
    Page<Song> findAll(Pageable pageable);

    @Query(value = "Select * from songs where genre_id=:genreId",
            countQuery = "Select count(*) from songs where genre_id=genreId", nativeQuery = true)
    Page<Song> findByGenre(Long genreId, Pageable pageable);

    @Query(value = "Select * from songs where lower(song_name) like lower(concat('%', :name, '%'))"
            , countQuery = "Select count(*) from songs where lower(song_name) like lower(concat('%', :name, '%'))"
            , nativeQuery = true)
    Page<Song> findByName(String name, Pageable pageable);
}

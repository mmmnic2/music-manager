package com.firstversion.musicmanager.repository;

import com.firstversion.musicmanager.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "Select * from genres where lower(genre_name) = lower(:genreName) ", nativeQuery = true)
    Genre checkExistByName(String genreName);
}

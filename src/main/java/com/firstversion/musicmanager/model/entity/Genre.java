package com.firstversion.musicmanager.model.entity;

import com.firstversion.musicmanager.dto.response.GenreResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;
    @Column(nullable = false)
    private String genreName;

    public GenreResponse toGenreResponse() {
        GenreResponse response = new GenreResponse();
        response.setGenreId(this.getGenreId());
        response.setGenreName(this.getGenreName());
        response.setCreateDate(this.getCreateDate());
        response.setModifiedDate(this.getModifiedDate());
        return response;
    }
}

package com.firstversion.musicmanager.model.entity;

import com.firstversion.musicmanager.dto.response.SongResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "songs")
public class Song extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    @Column(nullable = false)
    private String songName;
    private String image;
    private String src;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public SongResponse toSongResponse() {
        SongResponse response = new SongResponse();
        response.setSongId(this.getSongId());
        response.setSongName(this.getSongName());
        response.setImage(this.getImage());
        response.setSrc(this.getSrc());
        response.setCreateDate(this.getCreateDate());
        response.setModifiedDate(this.getModifiedDate());
        response.setGenre(this.getGenre().toGenreResponse());
        response.setAuthor(this.getAuthor().toAuthorResponse());
        return response;
    }

}

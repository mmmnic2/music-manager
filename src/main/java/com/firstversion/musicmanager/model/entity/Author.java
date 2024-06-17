package com.firstversion.musicmanager.model.entity;

import com.firstversion.musicmanager.dto.response.AuthorResponse;
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
@Table(name = "authors")
public class Author extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;
    @Column(nullable = false)
    private String authorName;

    public AuthorResponse toAuthorResponse() {
        AuthorResponse response = new AuthorResponse();
        response.setAuthorId(this.getAuthorId());
        response.setAuthorName(this.getAuthorName());
        response.setCreateDate(this.getCreateDate());
        response.setModifiedDate(this.getModifiedDate());
        return response;
    }
}

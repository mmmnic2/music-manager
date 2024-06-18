package com.firstversion.musicmanager.dto.response;

import com.firstversion.musicmanager.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongResponse extends BaseDTO {
    private Long songId;
    private String songName;
    private String image;
    private String src;
    private GenreResponse genre;
    private AuthorResponse author;
}

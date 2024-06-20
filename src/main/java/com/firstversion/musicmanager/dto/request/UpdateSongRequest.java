package com.firstversion.musicmanager.dto.request;

import lombok.Data;

@Data
public class UpdateSongRequest {
    private String songName;
    private Long genreId;
}

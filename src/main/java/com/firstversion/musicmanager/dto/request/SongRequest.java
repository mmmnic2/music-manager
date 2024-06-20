package com.firstversion.musicmanager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongRequest {
    private Long songId;
    private String songName;
    private MultipartFile image;
    private MultipartFile src;
    private Long genreId;
}

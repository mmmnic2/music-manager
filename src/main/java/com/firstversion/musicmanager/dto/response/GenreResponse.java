package com.firstversion.musicmanager.dto.response;

import com.firstversion.musicmanager.dto.BaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreResponse extends BaseDTO {
    private Long genreId;
    private String genreName;
}

package com.firstversion.musicmanager.dto.response;

import com.firstversion.musicmanager.dto.BaseDTO;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse extends BaseDTO {
    private Long authorId;
    private String authorName;
}

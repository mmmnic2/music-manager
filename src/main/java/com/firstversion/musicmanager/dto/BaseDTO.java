package com.firstversion.musicmanager.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
public abstract class BaseDTO {
//    private String createBy;
    private Date createDate;
//    private String modifiedBy;
    private Date modifiedDate;
}

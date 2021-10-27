package org.wecango.wecango.Base.AdminContent.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminContentSaveReqDto {
    int id;

    String title;

    String markDown;

    String html;

    String category;

    Integer orderIdx;
}

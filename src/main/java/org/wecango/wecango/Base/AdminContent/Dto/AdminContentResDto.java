package org.wecango.wecango.Base.AdminContent.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminContentResDto {
    int id;

    String title;

    String markDown;

    String html;

    LocalDateTime updateTime;

    LocalDateTime createTime;

    String category;

    Integer orderIdx;
}


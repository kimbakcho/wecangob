package org.wecango.wecango.Base.AdminContent.Dto;

import java.time.LocalDateTime;

public interface AdminContentSimpleResDto {
    int getId();

    String getTitle();

    LocalDateTime getUpdateTime();

    LocalDateTime getCreateTime();
}


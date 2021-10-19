package org.wecango.wecango.Base.Common;

import lombok.Data;

import java.util.List;

@Data
public class PageReqDto {
    Integer page;
    Integer size;
    List<SortReqDto> sorts;
}

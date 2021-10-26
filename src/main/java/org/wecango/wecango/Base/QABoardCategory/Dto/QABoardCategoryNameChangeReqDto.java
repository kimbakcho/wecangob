package org.wecango.wecango.Base.QABoardCategory.Dto;

import lombok.Data;

@Data
public class QABoardCategoryNameChangeReqDto {
    String oldCategoryName;
    String newCategoryName;
}

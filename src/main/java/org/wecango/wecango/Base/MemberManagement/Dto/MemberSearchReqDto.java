package org.wecango.wecango.Base.MemberManagement.Dto;

import lombok.Data;
import org.wecango.wecango.Base.Common.PageReqDto;

@Data
public class MemberSearchReqDto {
    String nickName;
    PageReqDto pageReqDto;
}

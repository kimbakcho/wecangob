package org.wecango.wecango.Base.MemberManagement.Dto;

import lombok.Data;

@Data
public class MemberJoinReqDto {
    String snsUid;

    String fromJoin;

    String nickName;

    String profileImage;

    String snsSignToken;
}

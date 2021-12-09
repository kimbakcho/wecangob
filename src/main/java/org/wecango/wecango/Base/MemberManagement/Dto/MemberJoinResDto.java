package org.wecango.wecango.Base.MemberManagement.Dto;

import lombok.Data;

@Data
public class MemberJoinResDto {
    MemberManagementSimpleResDto memberManagementSimpleResDto;
    String token;
}

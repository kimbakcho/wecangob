package org.wecango.wecango.Base.MemberManagement.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberManagementSimpleResDto {
    String uid;
    String nickName;
    String realName;
    boolean vaccineCertificateRegistration;
    boolean userActivationStatus;
    String profileImage;
    String role;
}

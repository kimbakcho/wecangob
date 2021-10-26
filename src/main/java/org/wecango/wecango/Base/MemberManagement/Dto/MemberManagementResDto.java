package org.wecango.wecango.Base.MemberManagement.Dto;

import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberManagementResDto {
    String uid;
    String fromJoin;
    String nickName;
    String realName;
    String password;
    LocalDateTime joinDate;
    String email;
    String phoneNumber;
    boolean vaccineCertificateRegistration;
    boolean userActivationStatus;
    String note;
    String profileImage;
    LocalDate birthDate;
    String role;
    String fcmToken;
}

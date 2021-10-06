package org.wecango.wecango.Base.MemberManagement.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "memberManagement")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberManagement {
    @Id
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

    public void setPassword(String password) {
        this.password = password;
    }
}

package org.wecango.wecango.Base.UserAlarm.Domain;

import lombok.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "userAlarm")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserAlarm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @JoinColumn(name = "userUid")
    @ManyToOne
    MemberManagement userUid;
    String message;
    LocalDateTime sendDateTime;
    boolean readFlag;
    @JoinColumn(name = "relationNationId")
    @ManyToOne
    NationControl relationNationId;

}

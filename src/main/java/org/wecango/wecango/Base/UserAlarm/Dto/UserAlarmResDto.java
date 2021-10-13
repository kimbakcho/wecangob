package org.wecango.wecango.Base.UserAlarm.Dto;

import lombok.Data;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
public class UserAlarmResDto {
    Integer id;
    MemberManagementSimpleResDto userUid;
    String message;
    LocalDateTime sendDateTime;
    boolean readFlag;
    NationControlResDto relationNationId;

}

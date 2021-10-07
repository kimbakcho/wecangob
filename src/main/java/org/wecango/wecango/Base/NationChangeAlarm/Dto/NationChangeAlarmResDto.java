package org.wecango.wecango.Base.NationChangeAlarm.Dto;

import lombok.Data;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;

import java.time.LocalDateTime;

@Data
public class NationChangeAlarmResDto {
    Integer id;
    MemberManagementSimpleResDto userUid;
    NationControlResDto nationControl;
    LocalDateTime createTime;
}

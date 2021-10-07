package org.wecango.wecango.Base.AlarmTravelFlag.Dto;

import lombok.Data;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;

@Data
public class AlarmTravelFlagResDto {
    Integer id;
    MemberManagementSimpleResDto userUid;
    NationControlResDto nationId;
}

package org.wecango.wecango.Base.UserBookMarkingCountry.Dto;

import lombok.Data;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusSimpleResDto;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class UserBookMarkingCountryResDto {
    int id;
    NationControlResDto nationId;
    LocalDateTime subscriptionDateTime;
    Integer orderIdx;
    ImmigrationStatusSimpleResDto immigrationStatusSimpleResDto;
}

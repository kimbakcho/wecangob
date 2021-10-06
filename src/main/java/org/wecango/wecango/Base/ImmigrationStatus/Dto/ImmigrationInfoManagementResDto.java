package org.wecango.wecango.Base.ImmigrationStatus.Dto;

import lombok.Data;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class ImmigrationInfoManagementResDto {
    Integer id;
    NationControlResDto nationId;
    String classification;
    String contentMarkDown;
    String contentHtml;
    String updateDateTime;
}

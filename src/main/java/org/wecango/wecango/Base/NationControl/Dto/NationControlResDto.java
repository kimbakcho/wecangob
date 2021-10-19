package org.wecango.wecango.Base.NationControl.Dto;

import lombok.Data;

import javax.persistence.Id;

@Data
public class NationControlResDto {
    int id;
    String nationName;
    String isoCode;
    Boolean displayFlag;
    String flagImage;
    String fileName;
}

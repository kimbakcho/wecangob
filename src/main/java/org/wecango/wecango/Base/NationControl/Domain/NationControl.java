package org.wecango.wecango.Base.NationControl.Domain;

import lombok.Getter;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationStatus;

import javax.persistence.*;

@Entity
@Table(name="nationControl")
@Getter
public class NationControl {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String nationName;
    String isoCode;
    String displayFlag;
    String flagImage;
    String fileName;

}

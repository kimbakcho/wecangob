package org.wecango.wecango.Base.NationControl.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="nationControl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NationControl implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique=true)
    String nationName;
    String isoCode;
    Boolean displayFlag;
    String flagImage;
    String fileName;



}

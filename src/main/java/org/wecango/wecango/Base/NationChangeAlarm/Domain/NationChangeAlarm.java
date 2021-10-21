package org.wecango.wecango.Base.NationChangeAlarm.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nationChangeAlarm",uniqueConstraints =
        {@UniqueConstraint(name = "userUid_nationId",columnNames = {"userUid","nationId"}) })
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class NationChangeAlarm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @JoinColumn(name = "userUid")
    @ManyToOne
    MemberManagement userUid;

    @JoinColumn(name = "nationId")
    @ManyToOne
    NationControl nationId;

    LocalDateTime createTime;

}

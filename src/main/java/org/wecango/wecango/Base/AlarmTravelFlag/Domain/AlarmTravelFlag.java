package org.wecango.wecango.Base.AlarmTravelFlag.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import javax.persistence.*;

@Entity
@Table(name = "alarmTravelFlag",uniqueConstraints =
        {@UniqueConstraint(name = "userUid_nationId",columnNames = {"userUid","nationId"}) })
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class AlarmTravelFlag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @JoinColumn(name = "userUid")
    @ManyToOne
    MemberManagement userUid;

    @JoinColumn(name = "nationId")
    @ManyToOne
    NationControl nationId;

}

package org.wecango.wecango.Base.QABadReport.Domain;

import lombok.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "qaBadReport")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class QABadReport {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idx;

    @JoinColumn(name = "qaDoc")
    @ManyToOne
    QABoard qaDoc;

    @JoinColumn(name = "reportUser")
    @ManyToOne
    MemberManagement reportUser;

    LocalDateTime reportTime;


}

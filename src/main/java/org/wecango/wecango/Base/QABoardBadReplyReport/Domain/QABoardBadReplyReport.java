package org.wecango.wecango.Base.QABoardBadReplyReport.Domain;

import lombok.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.QABoardReply.Domain.QABoardReply;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qaBoardBadReplyReport")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class QABoardBadReplyReport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idx;
    @JoinColumn(name = "reply")
    @ManyToOne
    QABoardReply reply;

    @JoinColumn(name = "reportUser")
    @ManyToOne
    MemberManagement reportUser;

    LocalDateTime reportTime;

}

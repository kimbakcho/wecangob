package org.wecango.wecango.Base.QABoardReply.Domain;

import lombok.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qaBoardReply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class QABoardReply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @JoinColumn(name = "nationName", referencedColumnName = "nationName")
    @ManyToOne
    NationControl nationName;
    String qaBoardCategory;
    @JoinColumn(name = "boardId")
    @ManyToOne
    QABoard qaBoardId;

    @JoinColumn(name = "writer")
    @ManyToOne
    MemberManagement writer;

    String content;

    LocalDateTime updateDateTime;

    Integer representativeComment;
}

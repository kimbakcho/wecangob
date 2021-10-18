package org.wecango.wecango.Base.QABoard.Domain;

import lombok.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qaBoard")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class QABoard {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String classificationQuestions;

    @JoinColumn(name = "nationName", referencedColumnName = "nationName")
    @ManyToOne
    NationControl nationName;

    @JoinColumn(name = "writer")
    @ManyToOne
    MemberManagement writer;

    String title;

    String contentText;

    String contentImageUrl;

    Integer replyCount;

    Integer view;

    LocalDateTime updateDateTime;

    Integer representative;
}

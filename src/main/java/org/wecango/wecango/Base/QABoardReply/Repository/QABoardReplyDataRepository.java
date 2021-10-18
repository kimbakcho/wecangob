package org.wecango.wecango.Base.QABoardReply.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;
import org.wecango.wecango.Base.QABoardReply.Domain.QABoardReply;

import java.util.List;

public interface QABoardReplyDataRepository extends JpaRepository<QABoardReply,Integer> {

    List<QABoardReply> findByQaBoardIdOrderByRepresentativeCommentDescUpdateDateTimeAsc(QABoard qaBoard);

}

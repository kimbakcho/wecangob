package org.wecango.wecango.Base.QABoard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;

public interface QABoardDataRepository extends JpaRepository<QABoard,Integer> {
}

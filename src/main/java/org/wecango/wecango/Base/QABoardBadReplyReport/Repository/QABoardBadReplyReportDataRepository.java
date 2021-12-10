package org.wecango.wecango.Base.QABoardBadReplyReport.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.QABoardBadReplyReport.Domain.QABoardBadReplyReport;

public interface QABoardBadReplyReportDataRepository extends JpaRepository<QABoardBadReplyReport, Integer> {
}

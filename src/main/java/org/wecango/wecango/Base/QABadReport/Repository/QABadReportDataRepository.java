package org.wecango.wecango.Base.QABadReport.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.QABadReport.Domain.QABadReport;

public interface QABadReportDataRepository extends JpaRepository<QABadReport,Integer> {
}

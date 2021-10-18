package org.wecango.wecango.Base.QABoardCategory.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.QABoardCategory.Domain.QABoardCategory;

import java.util.List;

public interface QABoardCategoryDataRepository extends JpaRepository<QABoardCategory,String> {

    List<QABoardCategory> findAllByOrderByOrderIdxAsc();
}

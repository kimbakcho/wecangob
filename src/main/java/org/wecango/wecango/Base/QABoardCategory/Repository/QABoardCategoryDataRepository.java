package org.wecango.wecango.Base.QABoardCategory.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.QABoardCategory.Domain.QABoardCategory;

import java.util.List;

public interface QABoardCategoryDataRepository extends JpaRepository<QABoardCategory,Integer> {

    List<QABoardCategory> findAllByOrderByOrderIdxAsc();

    QABoardCategory getByCategoryName(String name);

    void deleteByCategoryName(String name);
}

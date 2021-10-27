package org.wecango.wecango.Base.AdminContent.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.AdminContent.Domain.AdminContent;
import org.wecango.wecango.Base.AdminContent.Dto.AdminContentSimpleResDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminContentDataRepository extends JpaRepository<AdminContent,Integer> {

    List<AdminContentSimpleResDto> findAllSimpleByOrderByCreateTimeDesc();

    List<AdminContentSimpleResDto> findAllSimpleByCategoryNotInOrderByOrderIdxDescCreateTimeDesc(List<String> nins);
}

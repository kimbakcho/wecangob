package org.wecango.wecango.Base.MemberManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;

public interface MemberManagementDataRepository extends JpaRepository<MemberManagement,String> {

    Boolean existsByNickName(String nickName);
}

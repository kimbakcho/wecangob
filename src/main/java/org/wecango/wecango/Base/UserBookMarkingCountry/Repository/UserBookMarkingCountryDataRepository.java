package org.wecango.wecango.Base.UserBookMarkingCountry.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.UserBookMarkingCountry.Domain.UserBookMarkingCountry;

import java.util.List;
import java.util.Optional;

public interface UserBookMarkingCountryDataRepository
        extends JpaRepository<UserBookMarkingCountry,Integer> {
    Optional<UserBookMarkingCountry> findByUserUidAndNationId(MemberManagement userUid, NationControl nationId);
    List<UserBookMarkingCountry> findByUserUidOrderByOrderIdxDesc(MemberManagement userUid);
    List<UserBookMarkingCountry> findByUserUid(MemberManagement userUid);
    void deleteByUserUidAndNationId(MemberManagement userUid, NationControl nationId);
}

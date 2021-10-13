package org.wecango.wecango.Base.UserAlarm.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.UserAlarm.Domain.QUserAlarm;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.wecango.wecango.Base.UserAlarm.Domain.QUserAlarm.userAlarm;

@Repository
public class UserAlarmQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UserAlarmQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void readAlarm(MemberManagement memberManagement){

        queryFactory.update(userAlarm)
                .set(userAlarm.readFlag,true)
                .where(userAlarm.userUid.eq(memberManagement))
                .execute();

    }

}

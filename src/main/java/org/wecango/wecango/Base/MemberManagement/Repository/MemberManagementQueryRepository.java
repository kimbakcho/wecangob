package org.wecango.wecango.Base.MemberManagement.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Domain.QMemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementResDto;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberSearchReqDto;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;

import javax.persistence.EntityManager;

import java.util.List;

import static org.wecango.wecango.Base.MemberManagement.Domain.QMemberManagement.memberManagement;

@Repository
public class MemberManagementQueryRepository extends QuerydslRepositorySupport {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberManagementQueryRepository(EntityManager em) {
        super(QMemberManagement.class);
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<MemberManagement> getMemberList(MemberSearchReqDto memberSearchReqDto,
                                                      Pageable pageable){


        BooleanBuilder booleanBuilder = new BooleanBuilder();


        if(memberSearchReqDto.getNickName() != null && !memberSearchReqDto.getNickName().isEmpty()){
            booleanBuilder.and(memberManagement.nickName.contains(memberSearchReqDto.getNickName()));
        }


        JPAQuery<MemberManagement> query = queryFactory.select(memberManagement)
                .from(memberManagement)
                .where(booleanBuilder);
        PathBuilder<MemberManagement> entityPath = new PathBuilder<>(MemberManagement.class, "memberManagement");
        for (Sort.Order order : pageable.getSort()) {
            PathBuilder<Object> path = entityPath.get(order.getProperty());
            query.orderBy(new OrderSpecifier(com.querydsl.core.types.Order.valueOf(order.getDirection().name()), path));
        }
        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());

        long totalCount = query.fetchCount();
        List<MemberManagement> results = query.fetch();

        return new PageImpl<MemberManagement>(results, pageable, totalCount);

    }

}

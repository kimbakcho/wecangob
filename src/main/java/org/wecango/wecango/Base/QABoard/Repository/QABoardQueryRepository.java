package org.wecango.wecango.Base.QABoard.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Domain.QMemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;
import org.wecango.wecango.Base.QABoard.Domain.QQABoard;
import org.wecango.wecango.Base.QABoard.Dto.QABoardFilterReqDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardResDto;

import javax.persistence.EntityManager;

import java.util.List;

import static org.wecango.wecango.Base.QABoard.Domain.QQABoard.qABoard;

@Repository
public class QABoardQueryRepository  extends QuerydslRepositorySupport {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    final NationControlDataRepository nationControlDataRepository;

    public QABoardQueryRepository(EntityManager em,NationControlDataRepository nationControlDataRepository) {
        super(QABoard.class);
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.nationControlDataRepository = nationControlDataRepository;
    }

    public Page<QABoard> getFilterDoc(QABoardFilterReqDto reqDto,Pageable pageable) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(reqDto.getMode() != null){
            if(reqDto.getMode().equals("title")){
                booleanBuilder.and(qABoard.title.contains(reqDto.getTitle()));
            }else if(reqDto.getMode().equals("writer")){
                MemberManagement writer = MemberManagement.builder()
                        .uid(reqDto.getWriter())
                        .build();
                booleanBuilder.and(qABoard.writer.eq(writer));
            }else if(reqDto.getMode().equals("content")){
                booleanBuilder.and(qABoard.contentText.contains(reqDto.getContent()));
            }else if(reqDto.getMode().equals("titleOrContent")){
                booleanBuilder.and(qABoard.title.contains(reqDto.getTitle())
                        .or(qABoard.contentText.contains(reqDto.getContent()))
                );
            }else if(reqDto.getMode().equals("nation")){
                NationControl nation = nationControlDataRepository.getByNationName(reqDto.getNation());
                booleanBuilder.and(qABoard.nationName.eq(nation));
            }
        }


        if((reqDto.getCategory() != null) && (!reqDto.getCategory().isEmpty())){
            booleanBuilder.and(qABoard.classificationQuestions.eq(reqDto.getCategory()));
        }

        JPAQuery<QABoard> query = queryFactory.select(qABoard)
                .from(qABoard)
                .where(booleanBuilder);

        long totalCount = query.fetchCount();
        List<QABoard> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<QABoard>(results, pageable, totalCount);
    }
}

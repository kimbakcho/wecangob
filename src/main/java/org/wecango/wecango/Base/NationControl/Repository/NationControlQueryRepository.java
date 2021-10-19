package org.wecango.wecango.Base.NationControl.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.QImmigrationStatus;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Domain.QNationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlReqDto;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NationControlQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public NationControlQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<NationControlResDto> getFilter(NationControlReqDto reqDto) {
        QNationControl qNationControl = QNationControl.nationControl;
        QImmigrationStatus qImmigrationStatus = QImmigrationStatus.immigrationStatus;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qImmigrationStatus.nationId.displayFlag.isTrue());

        if (reqDto.getContinent() != null && reqDto.getContinent().size() > 0) {
            builder.and(qImmigrationStatus.continent.in(reqDto.getContinent()));
        }

        if (reqDto.getCovidTest() != null) {
            builder.and(qImmigrationStatus.covidTest.eq(reqDto.getCovidTest()));
        }

        if (reqDto.getPossibleExempted() != null) {
            builder.and(qImmigrationStatus.possibleExempted.eq(reqDto.getPossibleExempted()));
        }

        if ((reqDto.getBenefitsVaccination()) != null && (reqDto.getBenefitsVaccination().size() > 0)) {
            builder.and(qImmigrationStatus.benefitsVaccination.in(reqDto.getBenefitsVaccination()));
        }

        if (reqDto.getTravelFlag() != null) {
            builder.and(qImmigrationStatus.travelFlag.eq(reqDto.getTravelFlag()));
        }

        if (reqDto.getVisaFlag() != null) {
            builder.and(qImmigrationStatus.visaFlag.eq(reqDto.getVisaFlag()));
        }

        if ((reqDto.getMandatoryQuarantine() != null) && (reqDto.getMandatoryQuarantine().size() > 0)) {
            builder.and(qImmigrationStatus.mandatoryQuarantine.in(reqDto.getMandatoryQuarantine()));
        }


        List<NationControl> fetch = queryFactory
                .select(qNationControl)
                .from(qNationControl)
                .leftJoin(qImmigrationStatus).on(qImmigrationStatus.nationId.id.eq(qNationControl.id))
                .where(builder)
                .fetch();

        ModelMapper modelMapper = new ModelMapper();

        List<NationControlResDto> collect = fetch.stream().map(item -> {
            return modelMapper.map(item, NationControlResDto.class);
        }).collect(Collectors.toList());

        return collect;
    }
}

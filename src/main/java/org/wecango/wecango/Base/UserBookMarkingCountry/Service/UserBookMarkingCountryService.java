package org.wecango.wecango.Base.UserBookMarkingCountry.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationStatus;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusSimpleResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Repository.ImmigrationStatusDataRepository;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;
import org.wecango.wecango.Base.UserBookMarkingCountry.Domain.UserBookMarkingCountry;
import org.wecango.wecango.Base.UserBookMarkingCountry.Dto.UserBookMarkingCountryResDto;
import org.wecango.wecango.Base.UserBookMarkingCountry.Repository.UserBookMarkingCountryDataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserBookMarkingCountryService {
    final UserBookMarkingCountryDataRepository userBookMarkingCountryDataRepository;

    final NationControlDataRepository nationControlDataRepository;

    final ImmigrationStatusDataRepository immigrationStatusDataRepository;

    public UserBookMarkingCountryResDto isBookMarking(MemberManagement memberManagement, int nationId) {
        NationControl nationControl = nationControlDataRepository.findById(nationId).get();
        Optional<UserBookMarkingCountry> userBookMarkingCountryOptional = userBookMarkingCountryDataRepository.findByUserUidAndNationId(memberManagement, nationControl);
        if (userBookMarkingCountryOptional.isEmpty()) {
            return null;
        } else {
            ModelMapper modelMapper = new ModelMapper();
            UserBookMarkingCountryResDto userBookMarkingCountryResDto = modelMapper.map(userBookMarkingCountryOptional.get(), UserBookMarkingCountryResDto.class);
            return userBookMarkingCountryResDto;
        }
    }

    public UserBookMarkingCountryResDto bookMarking(MemberManagement memberManagement, int nationId) {
        NationControl nationControl = nationControlDataRepository.findById(nationId).get();
        List<UserBookMarkingCountry> byUserUidOrderByOrderIdxDesc = userBookMarkingCountryDataRepository.findByUserUidOrderByOrderIdxDesc(memberManagement);
        int orderIdx = 0;
        if (byUserUidOrderByOrderIdxDesc.size() > 0) {
            UserBookMarkingCountry userBookMarkingCountry = byUserUidOrderByOrderIdxDesc.get(0);
            orderIdx = userBookMarkingCountry.getOrderIdx() + 1;
        }

        UserBookMarkingCountry userBookMarkingCountry = UserBookMarkingCountry.builder()
                .nationId(nationControl)
                .orderIdx(orderIdx)
                .subscriptionDateTime(LocalDateTime.now())
                .userUid(memberManagement)
                .build();
        UserBookMarkingCountry save = userBookMarkingCountryDataRepository.save(userBookMarkingCountry);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(save, UserBookMarkingCountryResDto.class);
    }

    public void bookUnMarking(MemberManagement memberManagement, int nationId) {
        NationControl nationControl = nationControlDataRepository.findById(nationId).get();
        userBookMarkingCountryDataRepository.deleteByUserUidAndNationId(memberManagement, nationControl);
    }

    public List<UserBookMarkingCountryResDto> bookMarkingList(MemberManagement memberManagement) {
        List<UserBookMarkingCountry> byUserUid = userBookMarkingCountryDataRepository.findByUserUidOrderByOrderIdxAsc(memberManagement);
        ModelMapper modelMapper = new ModelMapper();
        List<UserBookMarkingCountryResDto> markingList = byUserUid.stream().map(
                x -> {
                    UserBookMarkingCountryResDto userBookMarkingCountryResDto = modelMapper.map(x, UserBookMarkingCountryResDto.class);
                    ImmigrationStatus immigrationStatus = immigrationStatusDataRepository.getByNationId(x.getNationId());
                    userBookMarkingCountryResDto.setImmigrationStatusSimpleResDto(modelMapper.map(immigrationStatus, ImmigrationStatusSimpleResDto.class));
                    return userBookMarkingCountryResDto;
                }
        ).collect(Collectors.toList());

        return markingList;
    }

    public void changeOrderIdx(MemberManagement memberManagement, int oldIndex, int newIndex) {
        UserBookMarkingCountry oldOrderIdx = userBookMarkingCountryDataRepository.getByUserUidAndOrderIdx(memberManagement, oldIndex);
        UserBookMarkingCountry newOrderIdx = userBookMarkingCountryDataRepository.getByUserUidAndOrderIdx(memberManagement, newIndex);
        newOrderIdx.setOrderIdx(-1);
        userBookMarkingCountryDataRepository.flush();
        oldOrderIdx.setOrderIdx(newIndex);
        userBookMarkingCountryDataRepository.flush();
        newOrderIdx.setOrderIdx(oldIndex);

    }
}

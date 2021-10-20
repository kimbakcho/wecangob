package org.wecango.wecango.Base.MemberManagement.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.FcmTokenUpdateReqDto;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberManagementService {
    final MemberManagementDataRepository memberManagementDataRepository;

    public MemberManagementSimpleResDto changeProfileImage(MemberManagement memberManagement, String imageUrl) {
        MemberManagement byId = memberManagementDataRepository.findById(memberManagement.getUid()).get();
        byId.setProfileImage(imageUrl);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(byId, MemberManagementSimpleResDto.class);
    }

    public String updateFcmToken(FcmTokenUpdateReqDto fcmTokenUpdateReqDto) throws FirebaseMessagingException {
        MemberManagement uid = memberManagementDataRepository.getById(fcmTokenUpdateReqDto.getUid());
        uid.setFcmToken(fcmTokenUpdateReqDto.getToken());
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add(fcmTokenUpdateReqDto.getToken());
        FirebaseMessaging.getInstance().subscribeToTopic(tokens,"all");
        return "ok";
    }

    public boolean checkNickName(String nickName) {
        return memberManagementDataRepository.existsByNickName(nickName);
    }

    public void changeNickName(MemberManagement memberManagement, String nickName) {
        MemberManagement byId = memberManagementDataRepository.getById(memberManagement.getUid());
        byId.setNickName(nickName);
    }
}

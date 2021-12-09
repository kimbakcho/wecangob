package org.wecango.wecango.Base.MemberManagement.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.Login.Service.JwtTokenBuilder;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.*;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementQueryRepository;
import org.wecango.wecango.Preference.CustomPreference;
import org.wecango.wecango.Security.JwtAuthenticationTokenProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberManagementService {
    final MemberManagementDataRepository memberManagementDataRepository;
    final MemberManagementQueryRepository memberManagementQueryRepository;
    final JwtAuthenticationTokenProvider jwtAuthenticationTokenProvider;
    final JwtTokenBuilder jwtTokenBuilder;
    final CustomPreference customPreference;
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

    public Page<MemberManagementResDto> getMemberList(MemberSearchReqDto memberSearchReqDto,Pageable pageable) {
        Page<MemberManagement> memberList = memberManagementQueryRepository.getMemberList(memberSearchReqDto, pageable);
        ModelMapper modelMapper = new ModelMapper();
        Page<MemberManagementResDto> resDtos = memberList.map(x -> {
            return modelMapper.map(x, MemberManagementResDto.class);
        });
        Page<MemberManagementResDto> collect = resDtos.map(x -> {
            x.setPassword("");
            return x;
        });
        return collect;
    }

    public void deleteMember(String uid) {
        memberManagementDataRepository.deleteById(uid);
    }

    public boolean isJoined(String uid) {
        return memberManagementDataRepository.existsById(uid);
    }

    public MemberJoinResDto join(MemberJoinReqDto joinReqDto) {
        if(memberManagementDataRepository.existsByNickName(joinReqDto.getNickName())){
            throw new IllegalArgumentException("nickName이 존재 합니다.");
        }

        if(!jwtAuthenticationTokenProvider.validateToken(joinReqDto.getSnsSignToken())){
            throw new AccessDeniedException("인증되지 않는 Token 입니다.");
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(customPreference.JWTSecurityKey())
                .parseClaimsJws(joinReqDto.getSnsSignToken());

        String uid = (String) claimsJws.getBody()
                .get("uid");

        if(!uid.equals(joinReqDto.getSnsUid())){
            throw new AccessDeniedException("인증되지 않는 사용자입니다.");
        }

        MemberManagement user = MemberManagement.builder()
                .uid(uid)
                .fromJoin(joinReqDto.getFromJoin())
                .nickName(joinReqDto.getNickName())
                .password("")
                .joinDate(LocalDateTime.now())
                .role("User")
                .userActivationStatus(false)
                .vaccineCertificateRegistration(false)
                .realName(joinReqDto.getNickName())
                .birthDate(LocalDate.now())
                .email("")
                .fcmToken("")
                .profileImage(joinReqDto.getProfileImage())
                .build();

        MemberManagement save = memberManagementDataRepository.save(user);
        ModelMapper modelMapper = new ModelMapper();

        MemberManagementSimpleResDto memberManagementSimpleResDto= modelMapper.map(save,MemberManagementSimpleResDto.class);
        MemberJoinResDto memberJoinResDto = new MemberJoinResDto();
        memberJoinResDto.setMemberManagementSimpleResDto(memberManagementSimpleResDto);
        memberJoinResDto.setToken(jwtTokenBuilder.buildToken(save));
        return memberJoinResDto;
    }
}

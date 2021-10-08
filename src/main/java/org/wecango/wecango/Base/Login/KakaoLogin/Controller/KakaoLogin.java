package org.wecango.wecango.Base.Login.KakaoLogin.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Base.Login.Service.JwtTokenBuilder;
import org.wecango.wecango.Preference.CustomPreference;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping("/kakaoLogin")
@RequiredArgsConstructor
public class KakaoLogin {

    final CustomPreference customPreference;

    final MemberManagementDataRepository memberManagementDataRepository;

    final JwtTokenBuilder jwtTokenBuilder;

    @GetMapping("/test")
    public void test(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        response.getWriter().println("test");
    }

    @GetMapping("/redirect")
    public void redirectLogin(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {

        String code = httpServletRequest.getParameter("code");

        RestTemplate kakaoAuthTemp = new RestTemplate();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", "42d9c5ccc2e2d85d76462e8c232a1b96");
        parameters.add("redirect_uri", customPreference.KakaoRedirect());
        parameters.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity formEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<Map> mapResponseEntity = kakaoAuthTemp.postForEntity("https://kauth.kakao.com/oauth/token", formEntity, Map.class);
        Map body = mapResponseEntity.getBody();

        parameters.clear();

        headers.clear();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth((String) body.get("access_token"));

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<Map> exchange = kakaoAuthTemp.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, request, Map.class);
        Map kakaoUserInfo = exchange.getBody();
        String id = "kakao_"+String.valueOf((int) kakaoUserInfo.get("id"));
        Map kakao_account = (Map)kakaoUserInfo.get("kakao_account");
        Map profile = (Map)kakao_account.get("profile");
        String nickname = (String) profile.get("nickname");
        if(nickname == null || nickname.isEmpty()){
            nickname = id;
        }
        String profile_image_url = (String) profile.get("profile_image_url");

        Optional<MemberManagement> byMember = memberManagementDataRepository.findById(id);
        MemberManagement member;
        if(byMember.isEmpty()){
            MemberManagement memberBuild = MemberManagement.builder()
                    .birthDate(LocalDate.now())
                    .email("")
                    .fcmToken("")
                    .fromJoin("Kakao")
                    .joinDate(LocalDateTime.now())
                    .nickName(nickname)
                    .profileImage(profile_image_url)
                    .realName(nickname)
                    .uid(id)
                    .userActivationStatus(false)
                    .vaccineCertificateRegistration(false)
                    .role("User")
                    .build();
            member = memberManagementDataRepository.save(memberBuild);
        }else {
            member = byMember.get();
        }

        Cookie myCookie = new Cookie("wSesstion",jwtTokenBuilder.buildToken(member));
        myCookie.setPath("/");
        myCookie.setMaxAge(86400);
        response.addCookie(myCookie);
        response.sendRedirect(customPreference.snsLoginRedirect());

    }



}

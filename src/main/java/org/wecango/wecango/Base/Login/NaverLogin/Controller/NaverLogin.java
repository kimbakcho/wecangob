package org.wecango.wecango.Base.Login.NaverLogin.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.wecango.wecango.Base.Login.Service.NickNameService;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Base.Login.Service.JwtTokenBuilder;
import org.wecango.wecango.Preference.CustomPreference;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/naverLogin")
@RequiredArgsConstructor
public class NaverLogin {

    final CustomPreference customPreference;

    final MemberManagementDataRepository memberManagementDataRepository;

    final JwtTokenBuilder jwtTokenBuilder;

    final NickNameService nickNameService;

    @GetMapping
    public void redirectLogin(String token,HttpServletResponse response) throws IOException, URISyntaxException {
        System.out.println(token);

        RestTemplate naverAuthLogin = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth((String) token);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<Map> exchange = naverAuthLogin.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET, request, Map.class);
        Map naverUserInfo = exchange.getBody();
        Map res = (Map)naverUserInfo.get("response");
        String id = "naver_"+ (String)res.get("id");
        String nickName = (String)res.get("nickname");
        if(nickName == null || nickName.isEmpty()){
            nickName = id;
        }
        String profile_image = (String)res.get("profile_image");
        if(profile_image != null){
            profile_image = profile_image.replaceAll("http","https");
        }

        String name = (String)res.get("name");
        if(name == null || name.isEmpty()){
            name = id;
        }

        name = nickNameService.getNickName(name);

        Optional<MemberManagement> byMember = memberManagementDataRepository.findById(id);
        MemberManagement member;

        if(byMember.isEmpty()) {
            MemberManagement memberBuild = MemberManagement.builder()
                    .birthDate(LocalDate.now())
                    .email("")
                    .fcmToken("")
                    .fromJoin("Naver")
                    .joinDate(LocalDateTime.now())
                    .nickName(nickName)
                    .profileImage(profile_image)
                    .realName(name)
                    .uid(id)
                    .userActivationStatus(false)
                    .vaccineCertificateRegistration(false)
                    .role("User")
                    .build();
            member = memberManagementDataRepository.save(memberBuild);
        }else {
            member = byMember.get();
        }

        URIBuilder builder = new URIBuilder(customPreference.snsLoginRedirect())
                .addParameter("uid", id)
                .addParameter("fromJoin","google")
                .addParameter("nickName",name);

        if(profile_image != null){
            builder.addParameter("profileImage",profile_image);
        }

        String signToken = jwtTokenBuilder.snsLoginSignToken(id);
        builder.addParameter("signToken",signToken);

        URI uriResult = builder.build();
        response.sendRedirect(uriResult.toString());
    }


}

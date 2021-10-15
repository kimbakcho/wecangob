package org.wecango.wecango.Base.Login.GoogleLogin.Controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecango.wecango.Base.Login.Service.NickNameService;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Base.Login.Service.JwtTokenBuilder;
import org.wecango.wecango.Preference.CustomPreference;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/googleLogin")
@RequiredArgsConstructor
public class GoogleLogin {
    final CustomPreference customPreference;

    final MemberManagementDataRepository memberManagementDataRepository;

    final JwtTokenBuilder jwtTokenBuilder;

    final NickNameService nickNameService;

    @GetMapping
    public void redirectLogin(String token, HttpServletResponse response) throws GeneralSecurityException, IOException {
        System.out.println(token);
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(customPreference.googleClientId()))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
        GoogleIdToken idToken = verifier.verify(token);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);
            String id = "google_"+userId;
            // Get profile information from payload
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            if(name== null || name.isEmpty()){
                name= id;
            }

            name = nickNameService.getNickName(name);

            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            if(pictureUrl != null){
                pictureUrl = pictureUrl.replaceAll("http","https");
            }

            Optional<MemberManagement> byMember = memberManagementDataRepository.findById(id);
            MemberManagement member;
            if(byMember.isEmpty()){
                MemberManagement memberBuild = MemberManagement.builder()
                        .birthDate(LocalDate.now())
                        .email("")
                        .fcmToken("")
                        .fromJoin("Google")
                        .joinDate(LocalDateTime.now())
                        .nickName(name)
                        .profileImage(pictureUrl)
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
            Cookie myCookie = new Cookie("wSesstion",jwtTokenBuilder.buildToken(member));
            myCookie.setPath("/");
            myCookie.setMaxAge(86400);
            myCookie.setDomain(customPreference.CookieDomain());
            response.addCookie(myCookie);
            response.sendRedirect(customPreference.snsLoginRedirect());
        }

    }


}

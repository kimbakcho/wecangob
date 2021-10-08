package org.wecango.wecango.Base.Login.WeCangoAdminLogin.Controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.wecango.wecango.Base.Login.Service.JwtTokenBuilder;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Preference.CustomPreference;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/weCanGoLogin")
@RequiredArgsConstructor
public class WeCanGoAdminLogin {

    final CustomPreference customPreference;

    final PasswordEncoder passwordEncoder;

    final MemberManagementDataRepository memberManagementDataRepository;

    final JwtTokenBuilder jwtTokenBuilder;

    @PostMapping("/adminLogin")
    public void adminLogin(@RequestParam String id,@RequestParam String pw, HttpServletResponse httpServletResponse) throws IOException {
        Optional<MemberManagement> byId = memberManagementDataRepository.findById(id);

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(customPreference.AdminLoginPage());

        if(byId.isEmpty()){
            uri.queryParam("error","없는 ID 입니다.");
            httpServletResponse.setStatus(401);
            UriComponents build = uri.build().encode(StandardCharsets.UTF_8);
            httpServletResponse.sendRedirect(build.toString());
            return ;
        }


        MemberManagement memberManagement = byId.get();
        if(!passwordEncoder.matches(pw,memberManagement.getPassword())) {
            uri.queryParam("error", "PW 가 틀렸습니다.");
            httpServletResponse.setStatus(401);
            UriComponents build = uri.build().encode(StandardCharsets.UTF_8);
            httpServletResponse.sendRedirect(build.toString());
            return;
        }


        Cookie myCookie = new Cookie("wSesstion",jwtTokenBuilder.buildToken(memberManagement));
        myCookie.setPath("/");
        myCookie.setMaxAge(86400);
        httpServletResponse.addCookie(myCookie);

        httpServletResponse.sendRedirect(customPreference.AdminPage());

    }


    @PostMapping("/adminSignIn")
    @ResponseBody
    public MemberManagementSimpleResDto signIn(@RequestParam String id, @RequestParam String pw,@RequestParam String nickname){

        MemberManagement build = MemberManagement.builder()
                .uid(id)
                .nickName(nickname)
                .realName(nickname)
                .fromJoin("wecango")
                .email("test@wecango.org")
                .password(passwordEncoder.encode(pw))
                .joinDate(LocalDateTime.now())
                .userActivationStatus(false)
                .vaccineCertificateRegistration(false)
                .role("Admin")
                .build();

        MemberManagement save = memberManagementDataRepository.save(build);
        ModelMapper mapper= new ModelMapper();
        return mapper.map(save,MemberManagementSimpleResDto.class);

    }



}

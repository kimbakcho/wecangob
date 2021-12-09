package org.wecango.wecango.Base.Login.SnsLogin.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.Login.Service.JwtTokenBuilder;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Preference.CustomPreference;
import org.wecango.wecango.Security.JwtAuthenticationTokenProvider;

@Service
@Transactional
@RequiredArgsConstructor
public class SnsLoginService {
    final JwtAuthenticationTokenProvider jwtAuthenticationTokenProvider;

    final JwtTokenBuilder jwtTokenBuilder;

    final CustomPreference customPreference;

    final MemberManagementDataRepository memberManagementDataRepository;


    public String getSnsLoginToken(String signToken) {
        if(jwtAuthenticationTokenProvider.validateToken(signToken)){
            Jws<Claims> claimsJws =
                    Jwts.parser().setSigningKey(customPreference.JWTSecurityKey()).parseClaimsJws(signToken);

            String uid = (String) claimsJws.getBody().get("uid");

            MemberManagement member = memberManagementDataRepository.getById(uid);

            return jwtTokenBuilder.buildToken(member);
        }else {
            throw new AccessDeniedException("Token이 잘못 되었습니다.");
        }
    }
}

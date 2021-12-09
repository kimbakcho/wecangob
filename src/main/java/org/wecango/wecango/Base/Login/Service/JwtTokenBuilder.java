package org.wecango.wecango.Base.Login.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Preference.CustomPreference;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenBuilder {

    final CustomPreference customPreference;

    public String buildToken(MemberManagement member) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plus(82600, ChronoUnit.SECONDS);
        Map<String,Object> claims = new LinkedHashMap<>();
        claims.put("role",member.getRole());
        claims.put("uid",member.getUid());
        claims.put("exp", Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()));
        return Jwts.builder()
                .setSubject(member.getUid())
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, customPreference.JWTSecurityKey())
                .setClaims(claims)
                .compact();
    }

    public String snsLoginSignToken(String id){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plus(82600, ChronoUnit.SECONDS);
        Map<String,Object> claims = new LinkedHashMap<>();
        claims.put("uid",id);
        claims.put("exp", Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()));
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, customPreference.JWTSecurityKey())
                .setClaims(claims)
                .compact();
    }
}

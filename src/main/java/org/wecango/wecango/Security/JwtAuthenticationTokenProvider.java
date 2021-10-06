package org.wecango.wecango.Security;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.wecango.wecango.Preference.CustomPreference;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenProvider implements AuthenticationTokenProvider{

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenProvider.class);

    final CustomPreference customPreference;

    @Override
    public String parseTokenString(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public String getTokenOwnerNo(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(customPreference.JWTSecurityKey())
                .parseClaimsJws(token)
                .getBody();
        return String.valueOf(claims.get("uid"));
    }

    @Override
    public boolean validateToken(String token) {
        if (StringUtils.hasText(token)) {
            try {
                Jwts.parser().setSigningKey(customPreference.JWTSecurityKey()).parseClaimsJws(token);
                return true;
            } catch (SignatureException e) {
                logger.error("Invalid JWT signature", e);
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT token", e);
            } catch (ExpiredJwtException e) {
                logger.error("Expired JWT token", e);
            } catch (UnsupportedJwtException e) {
                logger.error("Unsupported JWT token", e);
            } catch (IllegalArgumentException e) {
                logger.error("JWT claims string is empty.", e);
            }
        }
        return false;
    }

}

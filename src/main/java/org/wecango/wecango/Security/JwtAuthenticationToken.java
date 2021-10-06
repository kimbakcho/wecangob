package org.wecango.wecango.Security;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtAuthenticationToken implements AuthenticationToken{
    private String token;

}

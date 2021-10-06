package org.wecango.wecango.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.wecango.wecango.Security.Service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = authenticationTokenProvider.parseTokenString(request);
        if (authenticationTokenProvider.validateToken(token)) {
            String uid = authenticationTokenProvider.getTokenOwnerNo(token);
            try {
                AccountAdapter member = (AccountAdapter) userService.loadUserByUsername(uid);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(member,
                        member.getPassword(), member.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (UsernameNotFoundException e) {
                throw new UsernameNotFoundException("유효하지않은 인증토큰 입니다. 인증토큰 회원 정보 오류");
            }
        }
        filterChain.doFilter(request, response);
    }
}

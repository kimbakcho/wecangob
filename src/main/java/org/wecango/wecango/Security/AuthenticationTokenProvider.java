package org.wecango.wecango.Security;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationTokenProvider {
    /***
     * HTTP 요청에서 토큰 취득
     * @param request HTTP 요청
     * @return 토큰
     */
    String parseTokenString(HttpServletRequest request);



    /***
     * 토큰에서 userNo 취득
     * @param token 토큰
     * @return userNo
     */
    String getTokenOwnerNo(String token);

    /***
     * 토큰 유효성 검사
     * @param token 토큰
     * @return 유효여부
     */
    boolean validateToken(String token);
}

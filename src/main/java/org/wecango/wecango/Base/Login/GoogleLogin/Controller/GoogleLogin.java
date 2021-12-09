package org.wecango.wecango.Base.Login.GoogleLogin.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/googleLogin")
@RequiredArgsConstructor
public class GoogleLogin {
    final CustomPreference customPreference;

    final MemberManagementDataRepository memberManagementDataRepository;

    final JwtTokenBuilder jwtTokenBuilder;

    final NickNameService nickNameService;


    @GetMapping("/redirect")
    public void redirectLogin1(String code, HttpServletResponse response) throws URISyntaxException, IOException {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        parameters.add("client_id", "352727726767-nuvi74gsk08hf746r14c1i2j2s7l6d2o.apps.googleusercontent.com");
        parameters.add("client_secret", "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");
        parameters.add("grant_type", "authorization_code");
        parameters.add("redirect_uri", customPreference.googleRedirect());

        HttpEntity<MultiValueMap<String,String>> rest_request = new HttpEntity<>(parameters,headers);

        URI uri = URI.create("https://www.googleapis.com/oauth2/v4/token");

        ResponseEntity<String> rest_reponse;
        rest_reponse = restTemplate.postForEntity(uri, rest_request, String.class);
        String bodys = rest_reponse.getBody();
        ObjectMapper objectMapper =new ObjectMapper();
        Map<String,String> map = objectMapper.readValue(bodys, Map.class);

        HttpHeaders headers2 = new HttpHeaders();
        String access_token = "Bearer " + map.get("access_token");
        headers2.set("Authorization",access_token);
        HttpEntity rest_request2 = new HttpEntity<>(headers2);
        ResponseEntity<Map> exchange =
                restTemplate.exchange("https://www.googleapis.com/oauth2/v2/userinfo", HttpMethod.GET,rest_request2
                        , Map.class);
        Map userinfoBody = exchange.getBody();

        String id = "google_"+userinfoBody.get("id");

        URIBuilder builder = new URIBuilder(customPreference.snsLoginRedirect())
                .addParameter("uid", id)
                .addParameter("fromJoin","google");

        if(userinfoBody.get("given_name") != null){
            builder.addParameter("nickName",(String) userinfoBody.get("given_name"));
        }

        if(userinfoBody.get("picture") != null){
            builder.addParameter("profileImage",(String) userinfoBody.get("picture"));
        }

        String signToken = jwtTokenBuilder.snsLoginSignToken(id);
        builder.addParameter("signToken",signToken);

        URI uriResult = builder.build();
        response.sendRedirect(uriResult.toString());
    }

}

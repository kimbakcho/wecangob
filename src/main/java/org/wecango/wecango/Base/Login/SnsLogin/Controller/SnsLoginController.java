package org.wecango.wecango.Base.Login.SnsLogin.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.Login.SnsLogin.Service.SnsLoginService;

@RestController
@RequestMapping("/snsLogin")
@RequiredArgsConstructor
public class SnsLoginController {

    final SnsLoginService snsLoginService;

    @GetMapping("/snsLoginToken")
    String getSnsLoginToken(String signToken){
        return snsLoginService.getSnsLoginToken(signToken);
    }
}

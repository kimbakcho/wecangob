package org.wecango.wecango.Base.Health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthController {
    @GetMapping()
    public String health(){
        return "ok";
    }
}

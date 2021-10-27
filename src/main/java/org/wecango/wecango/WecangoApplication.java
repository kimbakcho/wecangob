package org.wecango.wecango;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WecangoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WecangoApplication.class, args);
    }

}

package org.wecango.wecango.Base.FireBase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;

@RestController
@RequestMapping("/fireBase")
@RequiredArgsConstructor
public class FireBaseController {

    final MemberManagementDataRepository memberManagementDataRepository;

    @PostMapping
    public String sendMessage(@RequestBody FcmMessageReqDto reqDto) throws FirebaseMessagingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonDto = objectMapper.writeValueAsString(reqDto);
        String token = null;
        Message message =null;
        if(reqDto.getType() !=null && !reqDto.getType().equals("All") ){
            MemberManagement member = memberManagementDataRepository.getById(reqDto.getUid());
            message = Message.builder()
                    .putData("payload",jsonDto)
                    .setToken(member.getFcmToken())
                    .build();
        }else {
            message = Message.builder()
                    .putData("payload",jsonDto)
                    .setTopic("all")
                    .build();
        }

        String send = FirebaseMessaging.getInstance().send(message);
        return "ok";
    }
}

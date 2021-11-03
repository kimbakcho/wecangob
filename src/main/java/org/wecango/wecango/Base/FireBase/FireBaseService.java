package org.wecango.wecango.Base.FireBase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Preference.CustomPreference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class FireBaseService {

    final MemberManagementDataRepository memberManagementDataRepository;

    public FireBaseService(CustomPreference customPreference,MemberManagementDataRepository memberManagementDataRepository) throws IOException {

        this.memberManagementDataRepository = memberManagementDataRepository;
        FileInputStream serviceAccount =
                new FileInputStream(customPreference.fireBaseAdminKeyPath());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }

    public void sendMessage(FcmMessageReqDto reqDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonDto = objectMapper.writeValueAsString(reqDto);
        Message message =null;

        if(!reqDto.getType().equals("All") ){
            MemberManagement member = memberManagementDataRepository.getById(reqDto.getUid());
            if((member.getFcmToken() != null) && (!member.getFcmToken().isEmpty())){

                message = Message.builder()
                        .putData("payload",jsonDto)
                        .setToken(member.getFcmToken())
                        .setApnsConfig(
                                ApnsConfig.builder()
                                        .setAps(Aps.builder()
                                                .setContentAvailable(true)
                                                .putCustomData("payload",jsonDto)
                                                .build())
                                        .build()
                        )
                        .build();
                try {
                    FirebaseMessaging.getInstance().send(message);
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }

        }else {
            message = Message.builder()
                    .putData("payload",jsonDto)
                    .setTopic("all")
                    .setApnsConfig(
                            ApnsConfig.builder()
                                    .setAps(Aps.builder()
                                            .setContentAvailable(true)
                                            .putCustomData("payload",jsonDto)
                                            .build())
                                    .build()
                    )
                    .build();
            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }
    }
}

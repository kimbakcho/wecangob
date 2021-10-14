package org.wecango.wecango.Base.Login.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class NickNameService {
    final MemberManagementDataRepository memberManagementDataRepository;

    public String getNickName(String initNickName){
        Boolean hasNickName = memberManagementDataRepository.existsByNickName(initNickName);
        String nickName = initNickName;
        if(hasNickName){
            Random rand = new Random();
            nickName = nickName+rand.nextInt(10000);
            return getNickName(nickName);
        }else {
            return nickName;
        }
    }
}

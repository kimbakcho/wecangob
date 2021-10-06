package org.wecango.wecango.Base.MemberManagement.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;

@Service
@RequiredArgsConstructor
public class MemberManagementService {
    final MemberManagementDataRepository memberManagementDataRepository;


}

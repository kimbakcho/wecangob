package org.wecango.wecango.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountAdapter extends User {
    MemberManagement memberManagement;

    public AccountAdapter(MemberManagement memberManagement){
        super(memberManagement.getUid(), memberManagement.getPassword(), authorities(memberManagement.getRole()));
        this.memberManagement = memberManagement; //주의!!
    }

    public MemberManagement getMemberManagement() {
        return memberManagement;
    }

    private static Collection<? extends GrantedAuthority> authorities(String roles) {
        Set<String> c = new HashSet<>();
        c.add(roles);
        return c.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toSet());
    }
}

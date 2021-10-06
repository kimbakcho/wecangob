package org.wecango.wecango.Security;

import lombok.Builder;
import lombok.Getter;

public interface AuthenticationToken {
    String getToken();
}

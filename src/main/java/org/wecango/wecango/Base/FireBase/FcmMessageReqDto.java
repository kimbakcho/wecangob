package org.wecango.wecango.Base.FireBase;

import lombok.Data;

@Data
public class FcmMessageReqDto {
    String type;
    String uid;
    String title;
    String message;
    String linkUrl;
    String nationId;
}

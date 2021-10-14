package org.wecango.wecango.Preference;

import org.springframework.stereotype.Component;

import java.util.Properties;
public interface CustomPreference {
    String getDataSourceUrl();
    Properties getJPAProperties();
    String getDataSourceUserName();
    String getDataSourcePW();
    String JWTSecurityKey();
    String KakaoRedirect();
    String snsLoginRedirect();
    String AWSAccessKey();
    String AWSSecretKey ();
    String AdminLoginPage();
    String AdminPage();
    String CookieDomain();
    String kakaoClientId();
    String googleClientId();
    String fireBaseAdminKeyPath();
}

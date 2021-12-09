package org.wecango.wecango.Preference;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Profile("local")
public class LocalPreference implements CustomPreference{
    @Override
    public String getDataSourceUrl() {
        return "jdbc:mysql://14.36.66.221:8822/wecango?serverTimezone=Asia/Seoul";
    }
    @Override
    public Properties getJPAProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.default_batch_fetch_size", "100");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB102Dialect");
        return properties;
    }

    @Override
    public String getDataSourceUserName() {
        return "wecango";
    }

    @Override
    public String getDataSourcePW() {
        return "wecango";
    }

    @Override
    public String JWTSecurityKey() {
        return "weCanGo1004";
    }

    @Override
    public String KakaoRedirect() {
        return "http://localhost:8181/kakaoLogin/redirect";
    }

    @Override
    public String snsLoginRedirect() {
        return "http://localhost:8080/UA002";
    }

    @Override
    public String AWSAccessKey() {
        return "AKIAQCABO75P6NONDG5B";
    }

    @Override
    public String AWSSecretKey() {
        return "BWhO+GM+scq2B9V+LctqIgsdp+LRgqgid2vYDlTm";
    }

    @Override
    public String AdminLoginPage() {
        return "http://localhost:8080/AdminLogin";
    }

    @Override
    public String AdminPage() {
        return "http://localhost:8080/WCAdmin";
    }

    @Override
    public String CookieDomain() {
        return "localhost";
    }

    @Override
    public String kakaoClientId() {
        return "42d9c5ccc2e2d85d76462e8c232a1b96";
    }


    @Override
    public String fireBaseAdminKeyPath() {
        return "C:\\\\workproject\\\\wecangob\\\\wecango-firebase-adminsdk-k7rvq-015913cec3.json";
    }

    @Override
    public String googleRedirect() {
        return "http://localhost:8181/googleLogin/redirect";
    }

}

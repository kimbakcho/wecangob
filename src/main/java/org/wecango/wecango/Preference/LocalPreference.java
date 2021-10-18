package org.wecango.wecango.Preference;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Profile("local")
public class LocalPreference implements CustomPreference{
    @Override
    public String getDataSourceUrl() {
        return "jdbc:mysql://ls-54b4e4c1dbf6e57414075808c604f5920b3c2f01.ceepas8p7edl.ap-northeast-2.rds.amazonaws.com/wecango?serverTimezone=Asia/Seoul";
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
        return "dbmasteruser";
    }

    @Override
    public String getDataSourcePW() {
        return "wecango1234";
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
        return "http://localhost:8080";
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
    public String googleClientId() {
        return "115231264132-tln84m4cvfiv8ii4p7mgkgvtd41mii1j.apps.googleusercontent.com";
    }

    @Override
    public String fireBaseAdminKeyPath() {
        return "C:\\\\workproject\\\\wecangob\\\\wecango-firebase-adminsdk-k7rvq-015913cec3.json";
    }

}

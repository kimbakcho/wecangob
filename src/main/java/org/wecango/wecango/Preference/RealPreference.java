package org.wecango.wecango.Preference;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Profile("real")
public class RealPreference implements CustomPreference{
    @Override
    public String getDataSourceUrl() {
        return "jdbc:mysql://ls-54b4e4c1dbf6e57414075808c604f5920b3c2f01.ceepas8p7edl.ap-northeast-2.rds.amazonaws.com/wecango?serverTimezone=Asia/Seoul";
    }

    @Override
    public Properties getJPAProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.use_sql_comments", "false");
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.default_batch_fetch_size", "256");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB102Dialect");
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
        return "https://api.wecango.org/kakaoLogin/redirect";
    }

    @Override
    public String snsLoginRedirect() {
        return "https://app.wecango.org";
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
        return "https://app.wecango.org/AdminLogin";
    }

    @Override
    public String AdminPage() {
        return "https://app.wecango.org/WCAdmin";
    }

    @Override
    public String CookieDomain() {
        return "wecango.org";
    }
}

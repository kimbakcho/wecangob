package org.wecango.wecango.Preference;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Profile("real")
public class RealPreference implements CustomPreference{
    @Override
    public String getDataSourceUrl() {
        return "jdbc:mysql://192.168.0.4:3306/wecango?serverTimezone=Asia/Seoul";
    }

    @Override
    public Properties getJPAProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.default_batch_fetch_size", "100");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB102Dialect");
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
        return "http://app.thkomeet.com:8181/kakaoLogin/redirect";
    }

    @Override
    public String snsLoginRedirect() {
        return "http://app.thkomeet.com:8383/wecango";
    }

    @Override
    public String googleClientId() {
        return "115231264132-tln84m4cvfiv8ii4p7mgkgvtd41mii1j.apps.googleusercontent.com";
    }
}

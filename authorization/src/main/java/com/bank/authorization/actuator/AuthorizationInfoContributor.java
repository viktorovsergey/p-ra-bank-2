package com.bank.authorization.actuator;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthorizationInfoContributor implements InfoContributor {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${spring.application.artifactId}")
    private String artifactId;

    @Value("${spring.application.version}")
    private String version;
    private String time = LocalDateTime.now().toString();

    @Override
    public void contribute(Info.Builder builder) {
        builder
                .withDetail("appName", appName)
                .withDetail("contextPath", contextPath)
                .withDetail("artifactId", artifactId)
                .withDetail("version", version)
                .withDetail("startTime", time);
    }


}

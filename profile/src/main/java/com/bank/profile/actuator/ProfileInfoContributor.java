package com.bank.profile.actuator;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileInfoContributor implements InfoContributor {

    @Value("${spring.application.name}")
    String appName;

    @Value("${info.artifactId}")
    String artifactId;

    final String startTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss (VV)"));

    @Value("${info.version}")
    String version;

    @Value("${server.servlet.context-path}")
    String contextPath;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("name", appName)
                .withDetail("artifactId", artifactId)
                .withDetail("startTime", startTime)
                .withDetail("version", version)
                .withDetail("contextPath", contextPath);
    }

}

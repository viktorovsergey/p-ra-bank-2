package com.bank.antifraud.actuator;

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
public class InfoWebEndpointExtension implements InfoContributor {

    @Value("${spring.application.name}")
    String appName;
    @Value("${info.artifactId}")
    String artifactId;
    @Value("${info.version}")
    String version;
    @Value("${server.servlet.context-path}")
    String contextPath;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("name", appName)
                .withDetail("artifactId", artifactId)
                .withDetail("version", version)
                .withDetail("contextPath", contextPath)
                .withDetail("startTime", getStartTime());
    }

    private static String getStartTime() {
        final ZonedDateTime now = ZonedDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss (VV)");
        return now.format(formatter);
    }
}

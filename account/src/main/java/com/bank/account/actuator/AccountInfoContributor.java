package com.bank.account.actuator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class AccountInfoContributor implements InfoContributor {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${info.artifactId}")
    private String artifactId;

    @Value("${info.version}")
    private String version;

    @Override
    public void contribute(Info.Builder builder) {
        final Instant startTime = Instant.now();
        final ZoneId zoneId = ZoneId.of("Europe/Moscow");
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(zoneId);
        final String formattedTime = formatter.format(startTime);

        builder
                .withDetail("name", applicationName)
                .withDetail("artifactId", artifactId)
                .withDetail("startTime", formattedTime)
                .withDetail("version", version)
                .withDetail("contextPath", contextPath);
    }
}

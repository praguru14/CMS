package com.cms.app.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import javax.sql.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
 class MyDBHealthIndidcator implements HealthIndicator {

    private final DataSource dataSource;
    @Value("${external.service.ip}")
    private String externalServiceIp;
    @Value("${URL}")
    private String url;


    @Autowired
    public MyDBHealthIndidcator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try {
            if (isApplicationHealthy() && isDatabaseHealthy()) {
                return Health.up()
                        .withDetail("Custom Detail", "The application and database are healthy.")
                        .build();
            } else {
                return Health.down()
                        .withDetail("Custom Detail", "The application or database is not healthy.")
                        .build();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isApplicationHealthy() throws IOException {
        log.info("Inside isApplicationHealthy{}", url);
        if(isExternalServiceReachable(url)){
            log.info("Eureka");
        }
        return isExternalServiceReachable(url);
    }

    private boolean isDatabaseHealthy() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }


    private boolean isExternalServiceReachable(String ipAddress) throws IOException {
        try {
//            InetAddress address = InetAddress.getByName(ipAddress);
            InetAddress address = InetAddress.getByName(new URL(url).getHost());
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            int responseCode = connection.getResponseCode();

            return address.isReachable(3000) && (responseCode >= 200 && responseCode < 300);
        } catch (IOException e) {
            return false;


        }

    }
}

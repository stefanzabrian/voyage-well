package com.dev.voyagewell.configuration.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vw.mail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailProperties {
    private String username;
    private String password;
    private boolean auth;
    private boolean starttls;
    private String host;
    private int port;
    private String trust;
}

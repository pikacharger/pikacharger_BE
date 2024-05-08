package elice04_pikacharger.pikacharger.domain.email.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Bean
    public JavaMailSender mailSender(){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("masterhyuk0613@gmail.com");
        mailSender.setPassword("yhjk lfnp anqa mezt");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.debug", true);
        javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        javaMailProperties.put("mail.smtp.ssl.protocol", "TLSv1.2");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
}

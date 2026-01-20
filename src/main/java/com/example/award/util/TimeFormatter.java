package com.example.award.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class TimeFormatter {

    @Bean
    DateTimeFormatter queryDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    }
}

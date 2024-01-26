package ru.gb.springdemo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("application.issue.max-allowed-books")
@Data
public class ReaderProperties {
    private Integer count;
}

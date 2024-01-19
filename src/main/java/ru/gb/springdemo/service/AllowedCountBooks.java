package ru.gb.springdemo.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class AllowedCountBooks {
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        if(count<1) count=getCount();
        this.count = count;
    }
}

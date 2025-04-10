package com.example.SmartShop.configuration;

import com.example.SmartShop.service.PopularityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {
    private final PopularityService popularityService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void calculatePopularityScores() {
        popularityService.calculatePopularityScores();
    }
}
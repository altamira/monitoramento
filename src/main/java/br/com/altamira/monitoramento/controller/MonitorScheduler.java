package br.com.altamira.monitoramento.controller;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;

public class MonitorScheduler {
	
	@Scheduled(fixedDelay = 10000)
    //@Scheduled(fixedRate = 5000)
    public void demoServiceMethod()
    {
        System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());
    }
}

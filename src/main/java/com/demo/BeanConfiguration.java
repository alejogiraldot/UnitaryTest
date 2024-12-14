package com.demo;

import com.demo.infrasctructure.repository.BankRepository;
import com.demo.infrasctructure.repository.CountRepository;
import com.demo.infrasctructure.service.CountService;
import com.demo.infrasctructure.service.CountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Autowired
    private CountRepository countRepository;
    @Autowired
    private BankRepository bankRepository;

    @Bean
    public CountService countService() {
        return new CountServiceImp(countRepository, bankRepository);
    }
}

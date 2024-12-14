package com.demo.infrasctructure.service;

import com.demo.domain.entity.Count;

import java.util.List;

public interface CountService {
    Count save (Count count);
    List<Count> findAll();
    Count findById(Long id);
    int checkTotalTransactions(Long id);
    int checkAmount(Long id);
    void transference(Long originAccount, Long destinationAccount, int amount, Long bankId);
}

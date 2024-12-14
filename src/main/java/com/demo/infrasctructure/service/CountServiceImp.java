package com.demo.infrasctructure.service;

import com.demo.infrasctructure.repository.BankRepository;
import com.demo.infrasctructure.repository.CountRepository;
import lombok.AllArgsConstructor;
import com.demo.domain.entity.Bank;
import com.demo.domain.entity.Count;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
public class CountServiceImp implements CountService {

    private final CountRepository countRepository;
    private final BankRepository bankRepository;

    @Override
    @Transactional
    public Count save(Count count) {
        return countRepository.save(count);
    }

    @Override
    public List<Count> findAll() {
        return countRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Count findById(Long id) {
        return countRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public int checkTotalTransactions(Long id) {
        Bank bank = bankRepository.findById(id).orElseThrow();
        return bank.getTotalTransactions();
    }

    @Override
    @Transactional(readOnly = true)
    public int checkAmount(Long id) {
        Count count = countRepository.findById(id).orElseThrow();
        return count.getAmount();
    }

    @Override
    @Transactional
    public void transference(Long originAccount, Long destinationAccount, int amount, Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow();
        int totalTransactions = bank.getTotalTransactions();
        bank.setTotalTransactions(++totalTransactions);
        bankRepository.save(bank);

        Count count = countRepository.findById(originAccount).orElseThrow();
        count.debit(amount);
        countRepository.save(count);

        Count destinyAccount = countRepository.findById(destinationAccount).orElseThrow();
        destinyAccount.credit(amount);
        countRepository.save(destinyAccount);

    }
}

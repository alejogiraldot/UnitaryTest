package com.demo.infrasctructure.repository;

import com.demo.domain.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    //List<Bank> findAll();
    //Bank findById(Long id);
    //void update(Bank bank);

}

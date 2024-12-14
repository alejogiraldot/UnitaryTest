package com.demo.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import com.demo.domain.exceptions.NotEnoughAmountException;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "count")
public class Count {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String person;
    private int amount;

    public void debit(int amount) {
        this.amount -= amount;
        if(this.amount < 0){
            throw new NotEnoughAmountException("Not enough amount");
        }
    }

    public void credit(int amount) {
        this.amount += amount;
    }
}


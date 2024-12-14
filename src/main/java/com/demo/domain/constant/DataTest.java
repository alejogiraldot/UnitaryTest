package com.demo.domain.constant;

import com.demo.domain.entity.Bank;
import com.demo.domain.entity.Count;

import java.util.Optional;

public class DataTest {
    public static Optional<Count> createCount001() {
        return Optional.of(new Count(1L, "Andres", 1000));
    }

    public static Optional<Count> createCount002() {
        return Optional.of(new Count(2L, "Juan", 2000));
    }

    public static Optional<Bank> createBank001() {
        return Optional.of(new Bank(1L, "Bancolombia", 0));
    }
}

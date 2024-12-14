package com.demo.dto.insfrastructure.service.service;

import com.demo.domain.constant.DataTest;
import com.demo.domain.entity.Count;
import com.demo.domain.exceptions.NotEnoughAmountException;
import com.demo.infrasctructure.repository.BankRepository;
import com.demo.infrasctructure.repository.CountRepository;
import com.demo.infrasctructure.service.CountServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountServiceTest {

    //@MockBean make same functionality of mock buy its library is from spring
    @Mock
    CountRepository countRepository;
    @Mock
    BankRepository bankRepository;
    @InjectMocks
    CountServiceImp countService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testDebitAmount() {
        when(countRepository.findById(1L)).thenReturn(DataTest.createCount001());
        when(countRepository.findById(2L)).thenReturn(DataTest.createCount002());
        when(bankRepository.findById(1L)).thenReturn(DataTest.createBank001());
        int ammountOrigin = countService.checkAmount(1L);
        int ammountDestiny = countService.checkAmount(2L);
        Assertions.assertEquals(1000, ammountOrigin);
        Assertions.assertEquals(2000, ammountDestiny);
        countService.transference(1L, 2L, 100, 1L);
        ammountOrigin = countService.checkAmount(1L);
        ammountDestiny = countService.checkAmount(2L);
        Assertions.assertEquals(900, ammountOrigin);
        Assertions.assertEquals(2100, ammountDestiny);
    }

    @Test
    void testNotEnoughAmmount() {
        when(countRepository.findById(1L)).thenReturn(DataTest.createCount001());
        when(bankRepository.findById(1L)).thenReturn(DataTest.createBank001());
        Exception exception = Assertions.assertThrows(NotEnoughAmountException.class, () -> {
            countService.transference(1L, 2L, 1300, 1L);
            ;
        });
        String wantedMessage = exception.getMessage();
        Assertions.assertEquals("Not enough amount", wantedMessage);
    }

    @Test
    void testFindById() {
        when(countRepository.findById(1L)).thenReturn(DataTest.createCount001());
        Assertions.assertEquals("Andres", countService.findById(1L).getPerson());
    }

    @Test
    void testFindAll() {
        List<Count> dataCount = Arrays.asList(DataTest.createCount001().orElseThrow(), DataTest.createCount002().orElseThrow()); // Agregar manejo del Optional
        when(countRepository.findAll()).thenReturn(dataCount);
        List<Count> allCounts = countService.findAll();
        Assertions.assertTrue(allCounts.contains(DataTest.createCount001().orElseThrow()));
    }

    @Test
    void testSave() {
        Count count = Count.builder()
                .id(null)
                .person("Pedro")
                .amount(300)
                .build();
        when(countRepository.save(any())).then(invocation -> {
            Count countInvocation = invocation.getArgument(0);
            countInvocation.setId(3L);
            return countInvocation;
        });

        Count count1 = countService.save(count);
        Assertions.assertEquals(3, count1.getId());
    }

    @Test
    void testCheckTransaction() {
        when(bankRepository.findById(1L)).thenReturn(DataTest.createBank001());
        int totalTransactions = countService.checkTotalTransactions(1L);
        Assertions.assertEquals(0, totalTransactions);
    }
}

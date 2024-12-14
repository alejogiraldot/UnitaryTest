package com.demo.dto.insfrastructure.api;

import com.demo.domain.constant.DataTest;
import com.demo.domain.dto.TransactionDto;
import com.demo.domain.entity.Count;
import com.demo.infrasctructure.api.CountController;
import com.demo.infrasctructure.service.CountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountController.class)
public class CountControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CountService service;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCountAll() throws Exception {
        List<Count> dataCount = Arrays.asList(DataTest.createCount001().orElseThrow(), DataTest.createCount002().orElseThrow()); // Agregar manejo del Optional
        when(service.findAll()).thenReturn(dataCount);
        mvc.perform(MockMvcRequestBuilders.get("/counts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].person").value("Andres"))
                .andExpect(jsonPath("$[1].person").value("Juan"));
        ;
    }

    @Test
    void testCount() throws Exception {
        Count expectedCount = DataTest.createCount001().orElseThrow(); // Agregar manejo del Optional
        when(service.findById(1L)).thenReturn(expectedCount);
        mvc.perform(MockMvcRequestBuilders.get("/counts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.person").value("Andres"));
    }

    @Test
    void testTransaction() throws Exception {
        TransactionDto transactionDto = TransactionDto.builder()
                .bankAccountId(1L)
                .countAmount(100)
                .countDestination(2L)
                .countOrigin(1L)
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/counts/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));

    }

    @Test
    void testSaveData() throws Exception {
        Count count = Count.builder()
                .id(null)
                .person("Pedro")
                .amount(300)
                .build();
        when(service.save(any())).then(invocation ->{
            //here can set a value when will return data
            Count countInvocation = invocation.getArgument(0);
            countInvocation.setId(3L);
            return countInvocation;
        });
        mvc.perform(MockMvcRequestBuilders.post("/counts/saveData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(count)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3L));

    }

}

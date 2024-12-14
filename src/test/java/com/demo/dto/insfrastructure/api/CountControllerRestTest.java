package com.demo.dto.insfrastructure.api;

import com.demo.domain.dto.TransactionDto;
import com.demo.domain.entity.Count;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountControllerRestTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testList() {
        TransactionDto transactionDto = TransactionDto.builder()
                .countOrigin(1L)
                .countDestination(2L)
                .countAmount(200)
                .bankAccountId(1L)
                .build();

        ResponseEntity<String> response = restTemplate.postForEntity("/counts/transaction", transactionDto, String.class);
        String json = response.getBody();
        Assertions.assertTrue(json.contains("Transaction Successful"));
    }

    @Test
    void testDetails() {
        ResponseEntity<Count> response = restTemplate.getForEntity("/counts/1", Count.class);
        Count count = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

    }

    @Test
    void testSave() {
        Count count = Count.builder()
                .person("Pedro")
                .id(null)
                .amount(700)
                .build();

        ResponseEntity<Count> response = restTemplate.postForEntity("/counts/saveData",count, Count.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("Pedro", response.getBody().getPerson());
    }


    @Test
    void testAllList() {
        ResponseEntity<Count[]> response = restTemplate.getForEntity("/counts", Count[].class);
        List<Count> count = Arrays.asList(response.getBody());
        Assertions.assertEquals("Andres", count.get(0).getPerson());

    }
}

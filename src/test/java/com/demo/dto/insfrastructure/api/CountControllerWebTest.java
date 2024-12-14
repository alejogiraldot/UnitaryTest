package com.demo.dto.insfrastructure.api;

import com.demo.domain.dto.TransactionDto;
import com.demo.domain.entity.Count;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountControllerWebTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testTransaction() {
        TransactionDto transactionDto = TransactionDto.builder()
                .countOrigin(1L)
                .countDestination(2L)
                .countAmount(200)
                .bankAccountId(1L)
                .build();

        webTestClient.post().uri("/counts/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Transaction Successful");
    }

    @Test
    void testDetail(){
        webTestClient.get().uri("/counts/1").exchange()
                .expectStatus()
                .isOk().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.person").isEqualTo("Andres");
    }

    @Test
    void testDetailAll(){
        webTestClient.get().uri("/counts").exchange()
                .expectStatus()
                .isOk().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$[0].person").isEqualTo("Andres")
                .jsonPath("$[1].person").isEqualTo("Juan");
    }

    @Test
    void testSave(){
        Count count = Count.builder()
                .person("Pedro")
                .id(null)
                .amount(700)
                .build();

        webTestClient.post().uri("/counts/saveData")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(count)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.person").isEqualTo("Pedro");
    }
}

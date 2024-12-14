package com.demo.dto.insfrastructure.repository;

import com.demo.domain.entity.Count;
import com.demo.infrasctructure.repository.CountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
public class CountJpaTest {

    @Autowired
    CountRepository countRepository;

    @Test
    void testFindById() {
        Optional<Count> count = countRepository.findById(1L);
        Assertions.assertTrue(count.isPresent());
        Assertions.assertEquals(count.get().getId(), 1L);
        Assertions.assertEquals("Andres", count.get().getPerson());
    }

    @Test
    void testFindByPerson() {
        Optional<Count> count = countRepository.findByPerson("Andres");
        Assertions.assertTrue(count.isPresent());
        Assertions.assertEquals(count.orElseThrow().getId(), 1L);
        Assertions.assertEquals("Andres", count.orElseThrow().getPerson());
    }

    @Test
    void testFindByPersonThrow() {
        Optional<Count> count = countRepository.findByPerson("Pedro");
        Assertions.assertThrows(NoSuchElementException.class, count::orElseThrow);

    }

    @Test
    void testFindAll() {
        List<Count> count = countRepository.findAll();
        Assertions.assertEquals(2, count.size());
    }

    @Test
    void testSave() {
        Count count = new Count(null, "Alejandro", 1100);
        countRepository.save(count);
        countRepository.findByPerson("Alejandro").orElseThrow();
        Assertions.assertEquals("Alejandro", count.getPerson());
    }

    @Test
    void testDelete() {
        countRepository.deleteById(3L);
        Optional<Count> count = countRepository.findByPerson("Alejandro");
        Assertions.assertThrows(NoSuchElementException.class, count::orElseThrow);
    }

}

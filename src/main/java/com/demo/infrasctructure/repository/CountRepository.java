package com.demo.infrasctructure.repository;

import com.demo.domain.entity.Count;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountRepository extends JpaRepository<Count, Long> {
    Optional<Count> findByPerson(String person);
}

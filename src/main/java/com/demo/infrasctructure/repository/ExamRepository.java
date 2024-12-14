package com.demo.infrasctructure.repository;

import com.demo.domain.dto.Exam;

import java.util.List;

public interface ExamRepository {
    List<Exam> findAll();
    Exam save(Exam exam);
}

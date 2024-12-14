package com.demo.infrasctructure.service;

import com.demo.domain.dto.Exam;

import java.util.Optional;

public interface ExamService {
    Optional<Exam> findByName(String name);
    Exam findExamByNameWithQuestion (String name);
    Exam saveExam(Exam exam);
}

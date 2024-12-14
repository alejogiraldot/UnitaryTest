package com.demo.infrasctructure.repository;

import java.util.List;

public interface QuestionRepository {
    List<String> findQuestionByExamId(Long id);
    void saveQuestion (List<String> question);
}

package com.demo.infrasctructure.service;

import com.demo.infrasctructure.repository.ExamRepository;
import com.demo.infrasctructure.repository.QuestionRepository;
import com.demo.domain.dto.Exam;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;
    private QuestionRepository questionRepository;

    @Override
    public Optional<Exam> findByName(String name) {
        return examRepository.findAll()
                .stream()
                .filter(exam -> exam.getName().contains(name)).findFirst();
    }

    @Override
    public Exam findExamByNameWithQuestion(String name) {
        Optional<Exam> examOptional = findByName(name);
        Exam exam = null;
        if(examOptional.isPresent()) {
            exam = examOptional.orElseThrow();
            List<String> questions = questionRepository.findQuestionByExamId(exam.getId());
            exam.setQuestions(questions);
        }
        return exam;
    }

    @Override
    public Exam saveExam(Exam exam) {
        if(exam.getQuestions() != null && !exam.getQuestions().isEmpty()) {
            questionRepository.saveQuestion(exam.getQuestions());
        }
        return examRepository.save(exam);
    }
}

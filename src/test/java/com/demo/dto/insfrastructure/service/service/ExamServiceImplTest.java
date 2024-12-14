package com.demo.dto.insfrastructure.service.service;

import com.demo.infrasctructure.service.ExamServiceImpl;
import com.demo.domain.constant.ConstantTest;
import com.demo.domain.dto.Exam;
import com.demo.infrasctructure.repository.ExamRepository;
import com.demo.infrasctructure.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //used to enable mock annotations such as mock and injectMocks
class ExamServiceImplTest {

    @Mock
    ExamRepository examRepository;
    @Mock
    QuestionRepository questionRepository;
    @InjectMocks
    ExamServiceImpl examService;


    @Test
    void findByName() {

        when(examRepository.findAll()).thenReturn(ConstantTest.DATA);
        Optional<Exam> exam = examService.findByName("Mathematics");
        assertEquals("Mathematics", exam.orElseThrow().getName());
    }

    @Test
    void findByNameEmptyList() {

        when(examRepository.findAll()).thenReturn(Collections.emptyList());
        Optional<Exam> exam = examService.findByName("Mathematics");
        assertFalse(exam.isPresent());
    }

    @Test
        //when set anyLong  can test with a generic value, it makes more flexible the code
    void findQuestionsByExam() {
        when(examRepository.findAll()).thenReturn(ConstantTest.DATA);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(ConstantTest.QUESTIONS);
        Exam exam = examService.findExamByNameWithQuestion("Mathematics");
        assertEquals(6, exam.getQuestions().size());
    }

    @Test
    void testSaveExam() {
        //given
        when(examRepository.save(any(Exam.class))).then(new Answer<Exam>() { //implementation of mockInvocation to autogenerate and autoincrement value o id to
            //get more real the test
            Long secuence = 7L;

            @Override
            public Exam answer(InvocationOnMock invocationOnMock) throws Throwable {
                Exam exam = invocationOnMock.getArgument(0);
                exam.setId(secuence++);
                return exam;
            }
        });
        //When
        Exam exam = examService.saveExam(ConstantTest.EXAM);
        //then
        assertNotNull(exam.getId());
        assertEquals(7L, exam.getId());
    }

    @Test
    void testSaveExamException() {
        when(examRepository.findAll()).thenReturn(ConstantTest.DATA_NULL_ID);
        when(questionRepository.findQuestionByExamId(isNull())).thenThrow(IllegalArgumentException.class); //isNull is an argument matcher and is used to general value null, is more optimal than set a null value directly
        assertThrows(IllegalArgumentException.class, () -> examService.findExamByNameWithQuestion("Mathematics"));

    }


    //ARGUMENT MATCHER:

    @Test
    void testArgumentMatcher() {
        when(examRepository.findAll()).thenReturn(ConstantTest.DATA_NULL_ID);
        when(questionRepository.findQuestionByExamId(isNull())).thenThrow(IllegalArgumentException.class); //isNull is an argument matcher and is used to general value null, is more optimal than set a null value directly
        assertThrows(IllegalArgumentException.class, () -> examService.findExamByNameWithQuestion("Mathematics"));

    }
}
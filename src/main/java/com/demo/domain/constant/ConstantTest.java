package com.demo.domain.constant;

import com.demo.domain.dto.Exam;

import java.util.Arrays;
import java.util.List;

public class ConstantTest {

    public static final List<Exam> DATA = Arrays.asList(Exam.builder()
                    .id(5L)
                    .name("Mathematics")
                    .build(),
            Exam.builder()
                    .id(6L)
                    .name("Language")
                    .build(),
            Exam.builder()
                    .id(7L)
                    .name("History")
                    .build());
    public static final List<Exam> DATA_NULL_ID = Arrays.asList(Exam.builder()
                    .id(null)
                    .name("Mathematics")
                    .build(),
            Exam.builder()
                    .id(null)
                    .name("Language")
                    .build(),
            Exam.builder()
                    .id(null)
                    .name("History")
                    .build());
    public static final List<String> QUESTIONS = Arrays.asList("spanish","English","French","German","Italian","Portuguese");
    public static final Exam EXAM = Exam.builder()
            .id(null)
            .name("Mathematics")
            .build();
}

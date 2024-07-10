package com.beyond.basic.domain;

import lombok.Data;

import java.util.List;

@Data
public class Student {
    private String name;
    private String email;
    private List<Student.Grade> grades;


    // Grade는 Student에 완전히 종속되어있는 클래스이므로 static 내부 클래스로도 선언할 수 있다.
    @Data
    public static class Grade {
        private String className;
        private String point;
    }
}

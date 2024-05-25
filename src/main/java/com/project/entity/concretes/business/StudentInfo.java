package com.project.entity.concretes.business;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.entity.concretes.user.User;
import com.project.entity.enums.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // UUID

    private Integer absentee; //yoklama bilgisi

    private Double midtermExam;

    private Double finalExam;

    private Double examAverage;

    private String infoNote;

    @Enumerated(EnumType.STRING)
    private Note letterGrade; // AA, aa, AA ,Aa, aA, Merhaba

    @ManyToOne // 1 Öğretmen ---> 100 öğrenci
    @JsonIgnore
    private User teacher;

    @ManyToOne // 1 Öğrenci aldığı ders sayısı kadar student infosu vardır.
    @JsonIgnore
    private User student;

    // NOT : Lesson - EducationTerm










}

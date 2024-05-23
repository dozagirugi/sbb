package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;         //질문번호

    @Column(length = 200)
    private String subject;     //제목

    @Column(columnDefinition = "TEXT")
    private String content;     //내용

    private LocalDateTime createDate; //작성일자

    //답변 리스트
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;  //한명이 여러개의 질문가능

    private LocalDateTime modifyDate; //수정일자

    @ManyToMany
    private Set<SiteUser> voter;
}

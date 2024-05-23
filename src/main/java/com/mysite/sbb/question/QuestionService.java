package com.mysite.sbb.question;

import com.mysite.sbb.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository qRepo;

    public List<Question> getAllQuestions() {
        return qRepo.findAll();
    }

    public Question getQuestionById(int id) {
        Optional<Question> q = qRepo.findById(id);
        if (q.isPresent()) {
            return q.get();
        } else {
            //id에 해당하는 질문을 못찾을 경우 에러를 발생하며 메세지 표시 , 404 상태코드
            throw new DataNotFoundException("question not found");
        }
    }
    //질문을 저장하기
    public void createQuestion(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user); //글쓴이 추가
        qRepo.save(q);
    }

    //페이지에 맞는 질문들을 가져옴 (사이즈 10개)
    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending()); //페이지번호, 한페이지10개

        return qRepo.findAll(pageable);
    }

    // 질문 수정하기
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        qRepo.save(question);
    }

    // 질문 삭제하기
    public void delete(Question question) {
        qRepo.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        qRepo.save(question);
    }
}

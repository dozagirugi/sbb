package com.mysite.sbb.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService uService;

    //회원가입 페이지를 보여줌 ( 빈 유저폼 객체를 전달 )
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    //회원 가입 post
    @PostMapping("signup")
    public String signup(@Valid UserCreateForm userCreateForm,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup_form"; //되돌아감
        }
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2","passwordInCorrect", "패스워드가 일치하지않음");
            return "signup_form";
        }
        //에러가 없을경우 DB에 새 유저를 저장하기
        try {
            uService.createUser(userCreateForm.getUsername(),
                                userCreateForm.getPassword1(),
                                userCreateForm.getEmail());
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/"; //리스트로
    }

    //로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
    //Post 검증은 시큐리티가 해준다.
}

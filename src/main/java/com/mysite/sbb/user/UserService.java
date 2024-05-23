package com.mysite.sbb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //새 유저를 저장(비밀번호 암호화)
    public SiteUser createUser(String username, String password, String email) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);
        //패스워드는 중요한 내용이기 때문에 바로 DB에 저장하지 않음.
        //비밀번호는 암호화 한다. 이때 암호화 객체 필요
        siteUser.setPassword(passwordEncoder.encode(password));

        userRepo.save(siteUser);
        return siteUser; // 저장한 유저객체를 리턴
    }

    //사용자명으로 유저객체를 리턴
    public SiteUser getUser(String username) {
        Optional<SiteUser> _siteUser = userRepo.findByUsername(username);
        if (_siteUser.isPresent()) {
            return _siteUser.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

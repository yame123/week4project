package com.sparta.week4project.service;

import com.sparta.week4project.dto.LoginRequestDto;
import com.sparta.week4project.dto.SignupRequestDto;
import com.sparta.week4project.entity.User;
import com.sparta.week4project.entity.UserRoleEnum;
import com.sparta.week4project.jwt.JwtUtil;
import com.sparta.week4project.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()){
            if(ADMIN_TOKEN.equals(requestDto.getAdminToken()))
                role = UserRoleEnum.ADMIN;
            else throw new IllegalArgumentException("관리자 토큰 값이 다릅니다.");
        }


        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }
        // 사용자 등록
        User user = new User(username, password,role);
        userRepository.save(user);
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username=requestDto.getUsername();
        String password=requestDto.getPassword();
        // 사용자 확인
        User user=userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        // 비밀번호 확인
        if(!password.equals(user.getPassword())){
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
        //JWT 생성, 쿠키 저장, Response 객체에 추가
        String token = jwtUtil.createToken(user.getUsername(),user.getRole());
        jwtUtil.addJwtToCookie(token,res);
    }
}

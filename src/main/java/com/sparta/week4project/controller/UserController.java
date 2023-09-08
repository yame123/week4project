package com.sparta.week4project.controller;

import com.sparta.week4project.dto.LoginRequestDto;
import com.sparta.week4project.dto.SignupRequestDto;
import com.sparta.week4project.exception.RestApiException;
import com.sparta.week4project.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/user/signup")
    public String signup(@Valid @RequestBody SignupRequestDto requestDto){
        userService.signup(requestDto);
        return "{\"msg\" : \"회원가입 성공\",\"statusCode\":200}";
    }

    @PostMapping("/user/login")
    public String login(@RequestBody LoginRequestDto requestDto,  HttpServletResponse res){
        userService.login(requestDto,res);

        return "{\"msg\" : \"로그인 성공\",\"statusCode\":200}";
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiException> handleException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}
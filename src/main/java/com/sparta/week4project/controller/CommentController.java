package com.sparta.week4project.controller;

import com.sparta.week4project.dto.CommentRequestDto;
import com.sparta.week4project.dto.CommentResponseDto;
import com.sparta.week4project.exception.RestApiException;
import com.sparta.week4project.jwt.JwtUtil;
import com.sparta.week4project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/comment")//포스팅 등록
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){

        return commentService.createComment(requestDto,tokenValue);
    }
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){

        return commentService.updateComment(id,requestDto, tokenValue);
    }

    @DeleteMapping("/comment/{id}")
    public Long deleteComment(@PathVariable Long id,@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){
        return commentService.deleteComment(id, tokenValue);
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


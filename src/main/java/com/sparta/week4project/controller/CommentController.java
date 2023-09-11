package com.sparta.week4project.controller;

import com.sparta.week4project.dto.CommentRequestDto;
import com.sparta.week4project.dto.CommentResponseDto;
import com.sparta.week4project.exception.RestApiException;
import com.sparta.week4project.jwt.JwtUtil;
import com.sparta.week4project.security.UserDetailsImpl;
import com.sparta.week4project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/comment")//댓글 등록
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.createComment(requestDto,userDetails.getUser());
    }
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.updateComment(id,requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comment/{id}")
    public Long deleteComment(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }


}


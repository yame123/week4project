package com.sparta.week4project.controller;

import com.sparta.week4project.dto.PostRequestDto;
import com.sparta.week4project.dto.PostResponseDto;
import com.sparta.week4project.exception.RestApiException;
import com.sparta.week4project.jwt.JwtUtil;
import com.sparta.week4project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/post")//모든 포스팅
    public List<PostResponseDto> getPosts(){

        return postService.getPosts();
    }


    @GetMapping("/post/{id}")//한개 포스팅
    public PostResponseDto getOnePost(@PathVariable Long id){

        return postService.getOnePost(id);
    }

    @PostMapping("/post")//포스팅 등록
    public PostResponseDto posting(@RequestBody PostRequestDto postRequestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){

        return postService.posting(postRequestDto,tokenValue);
    }
    //@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){

        return postService.updatePost(id,postRequestDto, tokenValue);
    }

    @DeleteMapping("/post/{id}")
    public Long deletePost(@PathVariable Long id,@RequestBody PostRequestDto postRequestDto,@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){
        return postService.deletePost(id, tokenValue);
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

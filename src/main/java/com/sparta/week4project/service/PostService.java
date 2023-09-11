package com.sparta.week4project.service;

import com.sparta.week4project.dto.PostRequestDto;
import com.sparta.week4project.dto.PostResponseDto;
import com.sparta.week4project.entity.Comment;
import com.sparta.week4project.entity.Post;
import com.sparta.week4project.entity.User;
import com.sparta.week4project.entity.UserRoleEnum;
import com.sparta.week4project.repository.CommentRepository;
import com.sparta.week4project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostResponseDto posting(PostRequestDto postRequestDto,User user) {
        Post post = new Post(postRequestDto);

        post.setUsername(user.getUsername());
        user.getPostList().add(post);

        Post savePost = postRepository.save(post);
        PostResponseDto postResponseDto = new PostResponseDto(savePost);
        return postResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto getOnePost(Long id) {
        Post post = findPost(id);
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = findPost(id);
        String username = user.getUsername();
        if (username.equals(post.getUsername())) {
            post.update(postRequestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        return new PostResponseDto(post);
    }



    public Long deletePost(Long id, User user) {
        Post post = findPost(id);
        String username = user.getUsername();
        List<Comment> commentList = post.getCommentList();
        if (username.equals(post.getUsername())||user.getRole()== UserRoleEnum.ADMIN) {
            commentRepository.deleteAll(commentList);
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        return id;
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }

//    private User findUser(String username) {
//        return userRepository.findByUsername(username).orElseThrow(() ->
//                new IllegalArgumentException("유저 정보를 찾을 수 없습니다.")
//        );
//    }
//
//    private String tokenToName (String tokenValue){
//        String token = jwtUtil.substringToken(tokenValue);
//        if (!jwtUtil.validateToken(token))
//            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
//        return jwtUtil.getUserInfoFromToken(token).get("sub").toString();
//    }
}

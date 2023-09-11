package com.sparta.week4project.service;

import com.sparta.week4project.dto.CommentRequestDto;
import com.sparta.week4project.dto.CommentResponseDto;
import com.sparta.week4project.entity.Comment;
import com.sparta.week4project.entity.Post;
import com.sparta.week4project.entity.User;
import com.sparta.week4project.entity.UserRoleEnum;
import com.sparta.week4project.jwt.JwtUtil;
import com.sparta.week4project.repository.CommentRepository;
import com.sparta.week4project.repository.PostRepository;
import com.sparta.week4project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;

@RestController
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {

        Comment comment = new Comment(requestDto);
        String username = user.getUsername();
        comment.setUsername(username);

        Post post = findPost(requestDto.getPostid());
        user.addComment(comment);
        post.addComment(comment);
        Comparator<Comment> compare = new Comparator<Comment>() {
            @Override
            public int compare(Comment a, Comment b) {
                return b.getCreatedAt().compareTo(a.getCreatedAt());
            }
        };

        Collections.sort(post.getCommentList(), compare);

        Comment saveComment = commentRepository.save(comment);

        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);
        return commentResponseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = findComment(id);
        String username = user.getUsername();

        if (username.equals(comment.getUsername())) {
            comment.update(requestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        return new CommentResponseDto(comment);
    }

    public Long deleteComment(Long id, User user) {
        Comment comment = findComment(id);
        String username = user.getUsername();
        if (username.equals(comment.getUsername())||user.getRole()== UserRoleEnum.ADMIN) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        return id;
    }

//    private String tokenToName (String tokenValue){
//        String token = jwtUtil.substringToken(tokenValue);
//        if (!jwtUtil.validateToken(token))
//            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
//        return jwtUtil.getUserInfoFromToken(token).get("sub").toString();
//    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new NullPointerException("선택한 댓글은 존재하지 않습니다.")
        );
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
}

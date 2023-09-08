package com.sparta.week4project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany
    @JoinColumn(name = "user_id") // users 테이블에 food_id 컬럼
    private List<Post> postList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id") // users 테이블에 food_id 컬럼
    private List<Comment> commentList = new ArrayList<>();




    public User(String username, String password, UserRoleEnum role) {
        this.username=username;
        this.password=password;
        this.role = role;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
}
package com.sparta.week4project.repository;

import com.sparta.week4project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

}


package com.webgroupEproject.myproject23526.Repository;

import com.webgroupEproject.myproject23526.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT COUNT(e) FROM Comment e")
    int countAll();
}

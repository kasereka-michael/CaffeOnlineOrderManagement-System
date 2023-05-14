package com.webgroupEproject.myproject23526.Services;

import com.webgroupEproject.myproject23526.Model.Comment;
import com.webgroupEproject.myproject23526.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    @Autowired
   private CommentRepository commentRepository;

    @Override
    public void saveClientcomment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getALLComments() {

        return commentRepository.findAll();
    }

    @Override
    public int getNumberofComment() {
        return commentRepository.countAll();
    }
}

package com.webgroupEproject.myproject23526.Services;

import com.webgroupEproject.myproject23526.Model.Comment;

import java.util.List;

public interface CommentService {
    void saveClientcomment(Comment comment);
    List<Comment> getALLComments();

    int getNumberofComment();

}

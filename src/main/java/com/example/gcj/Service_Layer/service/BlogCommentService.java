package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.blog_comment.BlogCommentListDTO;
import com.example.gcj.Service_Layer.dto.blog_comment.CreateBlogCommentDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

public interface BlogCommentService {
    void create(CreateBlogCommentDTO request, long blogId);
    PageResponseDTO<BlogCommentListDTO> get(Long blogId, Long parentCommentId, int pageNumber, int pageSize);

}

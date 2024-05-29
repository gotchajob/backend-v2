package com.example.gcj.service.impl;

import com.example.gcj.dto.blog.BlogListResponseDTO;
import com.example.gcj.dto.blog.BlogResponseDTO;
import com.example.gcj.dto.blog.CreateBlogDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Blog;
import com.example.gcj.model.Category;
import com.example.gcj.model.User;
import com.example.gcj.repository.BlogCommentRepository;
import com.example.gcj.repository.BlogRepository;
import com.example.gcj.service.BlogService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.mapper.BlogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final static int ACTIVE = 1;
    private final static int NUMBER_RELATE_BLOG = 3;

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final BlogCommentRepository blogCommentRepository;


    @Override
    public void createBlog(CreateBlogDTO request) {
        if (request == null) {
            throw new CustomException("Null request");
        }

        User author = userService.currentUser();
        if (author == null) {
            throw new CustomException("invalid author");
        }
        Blog blog = Blog.builder()
                .thumbnail(request.getThumbnail())
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .content(request.getContent())
                .category(Category.builder().id(request.getCategoryId()).build())
                .author(author)
                .status(ACTIVE)
                .build();

        blogRepository.save(blog);
    }

    @Override
    public PageResponseDTO<BlogListResponseDTO> blogList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<Blog> blogs = blogRepository.getAllByStatus(ACTIVE, pageable);

        return new PageResponseDTO<>( blogs.map(BlogMapper::toDto).toList(), blogs.getTotalPages());
    }

    @Override
    public BlogResponseDTO getBlog(long id) {
        Blog blog = blogRepository.getByIdAndStatus(id, ACTIVE);
        if (blog == null) {
            throw new CustomException("Not found");
        }
        long numberComment = blogCommentRepository.countByBlogIdAndParentCommentId(blog.getId(), null);

        List<Blog> relateBlogs = blogRepository.findRelateBlogs(blog.getCategory().getId(), blog.getId(), NUMBER_RELATE_BLOG);
        BlogResponseDTO response =  BlogMapper.toDto(blog, relateBlogs);
        response.setNumberComment(numberComment);
        return response;
    }
}

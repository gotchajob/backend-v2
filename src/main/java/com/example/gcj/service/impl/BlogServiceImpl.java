package com.example.gcj.service.impl;

import com.example.gcj.dto.blog.BlogListResponseDTO;
import com.example.gcj.dto.blog.BlogResponseDTO;
import com.example.gcj.dto.blog.CreateBlogDTO;
import com.example.gcj.dto.other.LikeDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Blog;
import com.example.gcj.model.BlogReaction;
import com.example.gcj.model.Category;
import com.example.gcj.model.User;
import com.example.gcj.repository.BlogCommentRepository;
import com.example.gcj.repository.BlogReactionRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final static int ACTIVE = 1;
    private final static int NUMBER_RELATE_BLOG = 3;

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final BlogCommentRepository blogCommentRepository;
    private final BlogReactionRepository blogReactionRepository;


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
    public PageResponseDTO<BlogListResponseDTO> blogList(Long categoryId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Blog> blogs = null;
        if (categoryId == null) {
            blogs = blogRepository.getAllByStatus(ACTIVE, pageable);
        } else {
            blogs = blogRepository.getAllByCategoryIdAndStatus(categoryId, ACTIVE, pageable);
        }
        return new PageResponseDTO<>(blogs.map(BlogMapper::toDto).toList(), blogs.getTotalPages());
    }

    @Override
    public BlogResponseDTO getBlog(long id) {
        User currentUser = userService.currentUser();
        boolean liked = false;
        Integer rated = 0;
        Blog blog = blogRepository.getByIdAndStatus(id, ACTIVE);
        if (blog == null) {
            throw new CustomException("Not found");
        }

        if (currentUser != null) {
            BlogReaction blogReaction = blogReactionRepository.findBlogReactionByBlogIdAndUserId(id, currentUser.getId());
            if (blogReaction != null) {
                liked = blogReaction.getReactionId() != null;
                rated = blogReaction.getRating() == null ? 0 : blogReaction.getRating();
            }
        }
        long likeCount = blogReactionRepository.countByBlogIdAndReactionIdNotNull(blog.getId());
        long numberComment = blogCommentRepository.countByBlogIdAndParentCommentId(blog.getId(), null);
        Double _averageRating = blogReactionRepository.findAverageRatingByBlogIdWhereRatingIsNotNull(id);
        double averageRating = _averageRating == null? 0 : _averageRating.doubleValue();
        long ratingQuantity = blogReactionRepository.countByBlogIdAndRatingNotNull(id);

        List<Blog> relateBlogs = blogRepository.findRelateBlogs(blog.getCategory().getId(), blog.getId(), NUMBER_RELATE_BLOG);
        BlogResponseDTO response = BlogMapper.toDto(blog, relateBlogs);
        LikeDTO likeDTO = LikeDTO.builder()
                .liked(liked)
                .value(likeCount)
                .build();
        response.setLikes(likeDTO);
        response.setNumberComment(numberComment);
        response.setAverageRating(averageRating);
        response.setRatingQuantity(ratingQuantity);
        response.setRated(rated);
        return response;
    }

    @Override
    public List<BlogListResponseDTO> findByCategoryId(long categoryId, int limit) {
        List<Blog> listBlog = blogRepository.findBlogByCategoryId(categoryId, limit);
        if (listBlog.isEmpty()) {
            throw new CustomException("No blog found with this id " + categoryId);
        }
        return listBlog.stream().map(BlogMapper::toDto).collect(Collectors.toList());
    }

}

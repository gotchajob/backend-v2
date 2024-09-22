package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Blog;
import com.example.gcj.Repository_Layer.model.BlogCategory;
import com.example.gcj.Repository_Layer.model.BlogReaction;
import com.example.gcj.Repository_Layer.model.User;
import com.example.gcj.Repository_Layer.repository.BlogCategoryRepository;
import com.example.gcj.Repository_Layer.repository.BlogCommentRepository;
import com.example.gcj.Repository_Layer.repository.BlogReactionRepository;
import com.example.gcj.Repository_Layer.repository.BlogRepository;
import com.example.gcj.Service_Layer.dto.blog.BlogListResponseDTO;
import com.example.gcj.Service_Layer.dto.blog.BlogResponseDTO;
import com.example.gcj.Service_Layer.dto.blog.CreateBlogRequestDTO;
import com.example.gcj.Service_Layer.dto.blog.UpdateBlogRequestDTO;
import com.example.gcj.Service_Layer.dto.other.LikeDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.mapper.BlogMapper;
import com.example.gcj.Service_Layer.service.BlogService;
import com.example.gcj.Service_Layer.service.PolicyService;
import com.example.gcj.Service_Layer.service.UserService;
import com.example.gcj.Shared.enums.PolicyKey;
import com.example.gcj.Shared.exception.CustomException;
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

    private final UserService userService;
    private final PolicyService policyService;

    private final BlogRepository blogRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private final BlogCommentRepository blogCommentRepository;
    private final BlogReactionRepository blogReactionRepository;


    @Override
    public void createBlog(CreateBlogRequestDTO request) {
        if (request == null) {
            throw new CustomException("Null request");
        }

        User author = userService.currentUser();
        if (author == null) {
            throw new CustomException("invalid author");
        }
        if (!blogCategoryRepository.existsById(request.getCategoryId())) {
            throw new CustomException("not found blog category with id " + request.getCategoryId());
        }


        Blog blog = Blog.builder()
                .thumbnail(request.getThumbnail())
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .content(request.getContent())
                .category(BlogCategory.builder().id(request.getCategoryId()).build())
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
        Integer numberRelateBlog = policyService.getByKey(PolicyKey.NUMBER_RELATE_BLOG, Integer.class);
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

        List<Blog> relateBlogs = blogRepository.findRelateBlogs(blog.getCategory().getId(), blog.getId(), numberRelateBlog);
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
    public boolean update(long id, UpdateBlogRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }
        if (!blogCategoryRepository.existsById(request.getCategoryId())) {
            throw new CustomException("not found blog category with id " + request.getCategoryId());
        }
        Blog blog = blogRepository.getById(id);

        blog.setCategory(BlogCategory.builder().id(request.getCategoryId()).build());
        blog.setContent(request.getContent());
        blog.setThumbnail(request.getThumbnail());
        blog.setShortDescription(request.getShortDescription());
        blog.setTitle(request.getTitle());
        blogRepository.save(blog);

        return true;
    }

}

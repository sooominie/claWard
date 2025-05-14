package min.claWard.service;

import jakarta.annotation.Resource;
import min.claWard.domain.Member;
import min.claWard.domain.Post;
import min.claWard.web.dto.PostCreateRequest;
import min.claWard.web.dto.PostResponse;
import min.claWard.web.dto.PostUpdateRequest;

import java.util.List;

public interface PostService {
    Post createPost(PostCreateRequest requestDto, Member member);

    void updatePost(Long postId, PostUpdateRequest requestDto, Member loginMember);

    void deletePost(Long postId, Member member);

    List<PostResponse> getAllPosts();

    PostResponse getPostById(Long postId);

    List<PostResponse> getMyPosts(Member member);

}

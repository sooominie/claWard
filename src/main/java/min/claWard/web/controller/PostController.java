package min.claWard.web.controller;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.Post;
import min.claWard.security.CustomUserDetails;
import min.claWard.service.PostService;
import min.claWard.web.dto.PostCreateRequest;
import min.claWard.web.dto.PostResponse;
import min.claWard.web.dto.PostUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostCreateRequest requestDto,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Post post = postService.createPost(requestDto, userDetails.getMember());
        return ResponseEntity.ok(post);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,
                                        @RequestBody PostUpdateRequest requestDto,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.updatePost(postId, requestDto, userDetails.getMember());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(postId, userDetails.getMember());
        return ResponseEntity.noContent().build();
    }

    // 게시글 전체 목록 조회
    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    // 게시글 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        PostResponse post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    // 로그인한 사용자의 게시글 목록 조회
    @GetMapping("/me/posts")
    public ResponseEntity<List<PostResponse>> getMyPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<PostResponse> myPosts = postService.getMyPosts(userDetails.getMember());
        return ResponseEntity.ok(myPosts);
    }
}

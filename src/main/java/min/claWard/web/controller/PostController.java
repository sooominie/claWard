package min.claWard.web.controller;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.Member;
import min.claWard.domain.Post;
import min.claWard.service.PostService;
import min.claWard.web.dto.PostCreateRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest request, @AuthenticationPrincipal Member loginUser) {
        return postService.create(request, loginUser);
    }
}


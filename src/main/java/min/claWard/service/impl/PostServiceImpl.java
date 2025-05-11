package min.claWard.service.impl;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.Member;
import min.claWard.domain.Post;
import min.claWard.repository.PostRepository;
import min.claWard.service.PostService;
import min.claWard.web.dto.PostCreateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post create(PostCreateRequest request, Member loginUser) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(loginUser)
                .build();
        return postRepository.save(post);
    }
}

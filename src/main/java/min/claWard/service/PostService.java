package min.claWard.service;

import min.claWard.domain.Member;
import min.claWard.domain.Post;
import min.claWard.web.dto.PostCreateRequest;

public interface PostService {
    Post create(PostCreateRequest request, Member loginUser);
}

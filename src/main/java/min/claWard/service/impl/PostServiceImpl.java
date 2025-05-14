package min.claWard.service.impl;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.Member;
import min.claWard.domain.Post;
import min.claWard.repository.PostRepository;
import min.claWard.service.PostService;
import min.claWard.web.dto.PostCreateRequest;
import min.claWard.web.dto.PostResponse;
import min.claWard.web.dto.PostUpdateRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post createPost(PostCreateRequest requestDto, Member member) {
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .author(member)
                .build();
        return postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequest requestDto, Member loginMember) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getAuthor().getId().equals(loginMember.getId())) {
            throw new AccessDeniedException("본인의 게시글만 수정할 수 있습니다.");
        }

        post.update(requestDto.getTitle(), requestDto.getContent());
    }

    @Override
    public void deletePost(Long postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getAuthor().getId().equals(member.getId())) {
            throw new SecurityException("본인의 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllOrderByCreatedAtDesc().stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor().getEmail())
                        .createdAt(post.getCreatedAt())
                        .hasFile(!post.getFiles().isEmpty())
                        .fileNames(post.getFiles().stream()
                                .map(f -> f.getFilename())
                                .collect(Collectors.toList()))
                        .fileIds(post.getFiles().stream()
                                .map(f -> f.getId())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getEmail())
                .createdAt(post.getCreatedAt())
                .hasFile(!post.getFiles().isEmpty())
                .fileNames(post.getFiles().stream()
                        .map(f -> f.getFilename())
                        .toList())
                .fileIds(post.getFiles().stream()
                        .map(f -> f.getId())
                        .toList())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getMyPosts(Member member) {
        return postRepository.findByAuthorIdOrderByCreatedAtDesc(member.getId()).stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor().getEmail())
                        .createdAt(post.getCreatedAt())
                        .hasFile(!post.getFiles().isEmpty())
                        .fileNames(post.getFiles().stream()
                                .map(f -> f.getFilename())
                                .toList())
                        .fileIds(post.getFiles().stream()
                                .map(f -> f.getId())
                                .toList())
                        .build())
                .toList();
    }
}

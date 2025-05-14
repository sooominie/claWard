package min.claWard.service.impl;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.Member;
import min.claWard.domain.Post;
import min.claWard.domain.UploadFile;
import min.claWard.repository.PostRepository;
import min.claWard.repository.UploadFileRepository;
import min.claWard.service.FileService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final UploadFileRepository uploadFileRepository;
    private final PostRepository postRepository;

    private final String uploadDir = "src/main/resources/static/uploads/";

    @Override
    public void uploadFile(Long postId, MultipartFile file) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 저장 경로 (절대 경로로 지정해야 함)
        String saveDir = System.getProperty("user.dir") + "/" + uploadDir;
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdirs(); // 디렉토리 없으면 생성
        }

        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String savedName = uuid + "_" + originalFilename;
        File target = new File(saveDir, savedName);

        try {
            file.transferTo(target); // 파일 실제 저장
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }

        // DB 저장
        UploadFile uploadFile = UploadFile.builder()
                .filename(originalFilename)
                .filepath("/uploads/" + savedName) // static 경로 기준으로 접근
                .post(post)
                .build();

        uploadFileRepository.save(uploadFile);
    }

    @Override
    public Resource downloadFile(Long fileId, Member member) {
        UploadFile file = uploadFileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("파일이 존재하지 않습니다."));

        Post post = file.getPost();
        if (!post.getAuthor().getId().equals(member.getId())) {
            throw new AccessDeniedException("파일에 접근 권한이 없습니다.");
        }

        String fullPath = System.getProperty("user.dir") + "/src/main/resources/static" + file.getFilepath();
        return new FileSystemResource(fullPath);
    }

}

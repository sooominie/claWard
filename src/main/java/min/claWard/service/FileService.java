package min.claWard.service;

import org.springframework.core.io.Resource;
import min.claWard.domain.Member;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void uploadFile(Long postId, MultipartFile file);

    Resource downloadFile(Long fileId, Member member);
}

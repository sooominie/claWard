package min.claWard.web.controller;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.UploadFile;
import min.claWard.domain.Member;
import min.claWard.repository.UploadFileRepository;
import min.claWard.security.CustomUserDetails;
import min.claWard.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
    private final UploadFileRepository uploadFileRepository;

    // 게시글에 파일 업로드
    @PostMapping("/upload/{postId}")
    public ResponseEntity<?> uploadFile(@PathVariable Long postId,
                                        @RequestParam("file") MultipartFile file) {
        fileService.uploadFile(postId, file);
        return ResponseEntity.ok().build();
    }

    // 파일 다운로드 (서비스 통해 접근)
    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        Resource resource = fileService.downloadFile(fileId, userDetails.getMember());

        String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // 파일 직접 경로 다운로드 (서비스 우회)
    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> directDownload(@PathVariable Long fileId) {
        UploadFile file = uploadFileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("파일이 존재하지 않습니다."));

        String filePath = System.getProperty("user.dir") + "/src/main/resources/static" + file.getFilepath();
        File systemFile = new File(filePath);
        if (!systemFile.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }

        Resource resource = new FileSystemResource(systemFile);

        // 실제 파일을 기반으로 MIME 타입 추론
        String mimeType;
        try {
            mimeType = Files.probeContentType(systemFile.toPath());
            if (mimeType == null) mimeType = "application/octet-stream"; // fallback
        } catch (IOException e) {
            mimeType = "application/octet-stream";
        }

        String contentDisposition = "attachment; filename=\"" + file.getFilename() + "\"";
        System.out.println("파일 실제 경로: " + filePath);
        System.out.println("MIME 타입: " + mimeType);
        System.out.println("파일 존재 여부: " + systemFile.exists());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);

    }


}
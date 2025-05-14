package min.claWard.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime createdAt;
    private boolean hasFile;
    private String content;
    private List<String> fileNames;
    private List<Long> fileIds;

    @Builder
    public PostResponse(Long id, String title, String content, String author,
                        LocalDateTime createdAt, boolean hasFile,
                        List<String> fileNames, List<Long> fileIds) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.hasFile = hasFile;
        this.fileNames = fileNames;  // 필드 초기화
        this.fileIds = fileIds;      // 필드 초기화
    }
}

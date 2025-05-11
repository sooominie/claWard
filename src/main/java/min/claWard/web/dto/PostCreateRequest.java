package min.claWard.web.dto;

import lombok.Data;

@Data
public class PostCreateRequest {
    private String title;
    private String content;
}

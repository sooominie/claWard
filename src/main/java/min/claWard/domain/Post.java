package min.claWard.domain;

import jakarta.persistence.*;                   // JPA 어노테이션
import lombok.*;                               // Lombok 어노테이션
import java.time.LocalDateTime;                // 날짜/시간
import java.util.ArrayList;
import java.util.List;

import min.claWard.domain.Member;             // 연관 엔티티
import min.claWard.domain.UploadFile;         // 연관 엔티티



@Entity
@Table(name = "post")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<UploadFile> files = new ArrayList<>();


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}



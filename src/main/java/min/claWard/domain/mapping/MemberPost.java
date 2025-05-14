package min.claWard.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import min.claWard.domain.Member;
import min.claWard.domain.Post;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") // 🔥 명시적으로 지정해줘야 Hibernate가 혼동 안 함
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id") // 🔥 마찬가지로 명시
    private Post post;


}

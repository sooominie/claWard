package min.claWard.repository;

import min.claWard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    // 이메일로 사용자 찾기
    Optional<Member> findByEmail(String email);
}

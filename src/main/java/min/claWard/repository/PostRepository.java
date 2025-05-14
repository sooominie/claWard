package min.claWard.repository;

import min.claWard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllOrderByCreatedAtDesc();
    List<Post> findByAuthorIdOrderByCreatedAtDesc(Long authorId);


}

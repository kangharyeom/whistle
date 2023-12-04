package company.whistle.domain.board.comment.repository;

import company.whistle.domain.board.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // contentId 단위 전체 조회
    @Query(value = "select * from comments where content_id = :contentId", nativeQuery = true)
    List<Comment> findAllByContentId(@Param("contentId") long contentId);

    // userId 단위 조회
    @Query(value = "select * from comments where user_id = :userId", nativeQuery = true)
    List<Comment> findByUserId(@Param("userId") long userId);
}

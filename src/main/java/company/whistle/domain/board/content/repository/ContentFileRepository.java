package company.whistle.domain.board.content.repository;

import company.whistle.domain.board.content.entity.ContentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentFileRepository extends JpaRepository<ContentFile, Long> {
    // contentId 단위 조회
    List<ContentFile> findByContentId(long contentId);

    // userId 단위 조회
    @Query(value = "select * from content_file where user_id = :userId", nativeQuery = true)
    List<ContentFile> findByUserId(@Param("userId") long userId);

    /*
    * 이미지 삭제 기능
    * select 이외의 쿼리는 @Modifying을 사용하여 구현해야함
    */
    @Modifying
    @Query(value = "delete from content_file where content_id=:contentId", nativeQuery = true)
    void deleteAllByContentId(@Param("contentId")Long contentId);
}
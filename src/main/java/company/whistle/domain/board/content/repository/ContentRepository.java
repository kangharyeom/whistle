package company.whistle.domain.board.content.repository;

import company.whistle.domain.board.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {
    // content 전체 조회
    List<Content> findAll();

    // userId 단위 조회
    @Query(value = "select * from contents where user_id = :userId", nativeQuery = true)
    List<Content> findByUserId(@Param("userId") long userId);

    // contentId 단위 조회
    Optional<Content> findByContentId(long contentId);

    /*
    * 게시글 검색 쿼리
    */
    @Query(value = "select * from contents where title like %:keyword% or content like %:keyword% ", nativeQuery = true)
    List<Content> findAllSearch(@Param(value = "keyword")String keyword);

    /*
     * category 단위 조회
     */
    @Query(value = "select * from contents where category_type like %:category%", nativeQuery = true)
    List<Content> findAllByCategoryType(@Param(value = "category")String category);

    /*
     * 게시글 작성자 조회
     */
    @Query(value = "select * from contents where name like :name", nativeQuery = true)
    List<Content> findAllSearchByUserName(@Param(value = "name")String name);

    /*
     * 최신 순서 게시글 조회
     */
    @Query(value = "select * from contents order by created_at desc", nativeQuery = true)
    List<Content> findContentsNewest();

    /*
     * 오래된 순서 게시글 조회
     */
    @Query(value = "select * from contents order by created_at asc", nativeQuery = true)
    List<Content> findContentsLatest();

}

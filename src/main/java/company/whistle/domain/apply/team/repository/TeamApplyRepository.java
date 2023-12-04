package company.whistle.domain.apply.team.repository;

import company.whistle.domain.apply.team.entity.TeamApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface TeamApplyRepository extends JpaRepository<TeamApply,Long> {
    // teamApplyId 단위 전체 조회
    @Query(value = "select * from team_applies where team_apply_id = :teamApplyId", nativeQuery = true)
    List<TeamApply> findAllByTeamApplyId(@Param("teamApplyId") long teamApplyId);

    // teamId 단위 조회
    @Query(value = "select * from team_applies where team_id = :teamId order by created_at desc", nativeQuery = true)
    List<TeamApply> findAllByTeamId(@Param("teamId") long teamId);

    /*
     * applyId 단위 조회
     * findVerified를 위해 Optional<LeagueApply>로 가져옴
     */
    Optional<TeamApply> findByTeamApplyId(@Param("teamApplyId") long teamApplyId);
}

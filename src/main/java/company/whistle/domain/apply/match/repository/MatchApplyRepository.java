package company.whistle.domain.apply.match.repository;

import company.whistle.domain.apply.match.entity.MatchApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface MatchApplyRepository extends JpaRepository<MatchApply,Long> {
    // applyId 단위 전체 조회
    @Query(value = "select * from match_applies where apply_id = :matchApplyId", nativeQuery = true)
    List<MatchApply> findAllByMatchApplyId(@Param("matchApplyId") long matchApplyId);

    // teamId 단위 조회
    @Query(value = "select * from match_applies where team_id = :teamId order by created_at desc", nativeQuery = true)
    List<MatchApply> findAllByTeamId(@Param("teamId") long teamId);

    // matchId 단위 조회
    @Query(value = "select * from match_applies where match_id = :matchId order by created_at desc", nativeQuery = true)
    List<MatchApply> findAllByMatchId(@Param("matchId") long matchId);

    // leagueId 단위 조회
    @Query(value = "select * from match_applies where league_id = :leagueId order by created_at desc", nativeQuery = true)
    List<MatchApply> findAllByLeagueId(@Param("leagueId") long leagueId);

    /*
     * matchApplyId 단위 조회
     * findVerified를 위해 Optional<LeagueApply>로 가져옴
     */
    Optional<MatchApply> findByMatchApplyId(@Param("matchApplyId") long matchApplyId);
}

package company.whistle.domain.match.unrank.repository;

import company.whistle.domain.match.unrank.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query(value = "select * from matches where match_id = :matchId", nativeQuery = true)
    List<Match> findAllByMatchId(@Param("matchId") long matchId);

    @Query(value = "select * from matches where team_id = :teamId", nativeQuery = true)
    List<Match> findAllByTeamId(@Param("teamId") long teamId);

    @Query(value = "select * from matches where league_id = :leagueId", nativeQuery = true)
    List<Match> findAllByLeagueId(@Param("leagueId") long leagueId);

    @Query(value = "select * from matches where user_id = :userId", nativeQuery = true)
    List<Match> findByUserId(@Param("userId") long userId);

    @Query(value = "select * from matches where schedule_id = :scheduleId", nativeQuery = true)
    List<Match> findByScheduleId(@Param("scheduleId") long scheduleId);

    Optional<Match> findByMatchId(long matchId);

    @Query(value = "select * from matches where title like %:keyword% or content like %:keyword% ", nativeQuery = true)
    List<Match> findAllSearch(@Param(value = "keyword")String keyword);

    @Query(value = "select * from matches where name like :name", nativeQuery = true)
    List<Match> findAllSearchByUserName(@Param(value = "name")String name);

    @Query(value = "select * from matches order by created_at desc", nativeQuery = true)
    List<Match> findMatchesNewest();

    @Query(value = "select * from matches order by created_at asc", nativeQuery = true)
    List<Match> findMatchesLatest();

    @Query(value = "select * from matches where team_id = :teamId", nativeQuery = true)
    Match findByVerifiedTeamId(@Param("teamId") long teamId);
}

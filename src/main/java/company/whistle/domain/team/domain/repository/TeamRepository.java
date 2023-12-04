package company.whistle.domain.team.domain.repository;

import company.whistle.domain.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query(value = "select * from teams where user_id = :userId", nativeQuery = true)
    List<Team> findByUserIdList(@Param("userId") long userId);

    @Query(value = "select * from teams where user_id = :userId", nativeQuery = true)
    Team findByUserId(@Param("userId") long userId);

    @Query(value = "select * from teams where team_Name = :teamName", nativeQuery = true)
    Team findByTeamName(@Param("teamName") String teamName);

    @Query(value = "select * from teams where match_id = :matchId", nativeQuery = true)
    List<Team> findByMatchId(@Param("matchId") long matchId);

    @Query(value = "select * from teams where league_id = :leagueId", nativeQuery = true)
    List<Team> findAllTeamsByLeagueId(@Param("leagueId") long leagueId);

    @Query(value = "select homeTeamName from teams where match_id = :matchId", nativeQuery = true)
    List<Team> findByMatchHomeId(@Param("matchId") long matchId);

    @Query(value = "select AwayTeamName from teams where match_id = :matchId", nativeQuery = true)
    List<Team> findByMatchAwayId(@Param("matchId") long matchId);

    // 명예 점수 상위 조회
    @Query(value = "SELECT * FROM teams ORDER BY honor_score DESC", nativeQuery = true)
    List<Team> findByHighestHonorScore();

    // 명예 점수 하위 조회
    @Query(value = "SELECT * FROM teams ORDER BY honor_score ASC", nativeQuery = true)
    List<Team> findByLowestHonorScore();

    Optional<Team> findByTeamId(Long teamId);

}

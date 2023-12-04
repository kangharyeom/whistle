package company.whistle.domain.team.squad.repository;

import company.whistle.domain.team.squad.entity.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SquadRepository extends JpaRepository<Squad, Long> {
    @Query(value = "select * from squads where league_id = :leagueId", nativeQuery = true)
    List<Squad> findAllTeamsByLeagueId(@Param("leagueId") long leagueId);

    @Query(value = "select * from squads where user_id = :userId", nativeQuery = true)
    List<Squad> findByUserId(@Param("userId") long userId);

    @Query(value = "select * from squads order by created_at desc", nativeQuery = true)
    List<Squad> findSquadNewest();

    @Query(value = "select * from squads order by created_at asc", nativeQuery = true)
    List<Squad> findSquadLatest();

    @Query(value = "select * from squads order by honor_score desc", nativeQuery = true)
    List<Squad> findHonorScore();

}

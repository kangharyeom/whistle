package company.whistle.domain.league.participants.repository;

import company.whistle.domain.league.participants.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
    @Query(value = "select * from Participants where league_id = :leagueId", nativeQuery = true)
    List<Participants> findAllLeaguesByLeagueId(@Param("leagueId") long leagueId);

    @Query(value = "SELECT * from Participants WHERE league_id = :leagueId order by league_match_points desc limit 1", nativeQuery = true)
    Participants findWinnerByLeagueId(@Param("leagueId") long leagueId);

    @Query(value = "SELECT * from Participants WHERE league_id = :leagueId order by league_match_points desc", nativeQuery = true)
    Participants findLeagueWinPointsByLeagueId(@Param("leagueId") long leagueId);

    @Query(value = "select league_lose_record from Participants where league_id = :leagueId", nativeQuery = true)
    Participants findLeagueLoseRecordByLeagueId(@Param("leagueId") long leagueId);

    @Query(value = "select * from Participants where user_id = :userId", nativeQuery = true)
    List<Participants> findByUserId(@Param("userId") long userId);

    @Query(value = "select * from Participants order by created_at desc", nativeQuery = true)
    List<Participants> findLeagueParticipantsNewest();

    @Query(value = "select * from Participants order by created_at asc", nativeQuery = true)
    List<Participants> findLeagueParticipantsLatest();

    @Query(value = "select * from Participants order by honor_score desc", nativeQuery = true)
    List<Participants> findHonorScore();

}

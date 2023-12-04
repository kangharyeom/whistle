package company.whistle.domain.match.league.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeagueMatchEndDto {
    private Long leagueId;
    private Long leagueMatchId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long homeTeamLeagueListId;
    private Long awayTeamLeagueListId;
    private Long homeTeamScore;
    private Long awayTeamScore;
    private String matchStatus;

    public void updateId(Long id){
        this.leagueMatchId = id;
    }
}

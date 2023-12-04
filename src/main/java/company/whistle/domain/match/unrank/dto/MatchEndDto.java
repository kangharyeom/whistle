package company.whistle.domain.match.unrank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MatchEndDto {
    private Long matchId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long homeTeamScore;
    private Long awayTeamScore;
    private String matchStatus;

    public void updateId(Long id){
        this.matchId = id;
    }
}

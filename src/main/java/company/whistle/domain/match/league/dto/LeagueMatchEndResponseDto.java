package company.whistle.domain.match.league.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class LeagueMatchEndResponseDto {
    private Long leagueMatchId;
    private Long homeTeamUserId;
    private Long awayTeamUserId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long homeTeamLeagueListId;
    private Long awayTeamLeagueListId;
    private Long homeTeamScore;
    private Long awayTeamScore;
    private String matchStatus;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Override
    public String toString() {
        return "LeagueMatchEndResponseDto{" +
                "leagueMatchId=" + leagueMatchId +
                ", homeTeamUserId=" + homeTeamUserId +
                ", awayTeamUserId=" + awayTeamUserId +
                ", homeTeamId=" + homeTeamId +
                ", awayTeamId=" + awayTeamId +
                ", homeTeamLeagueListId=" + homeTeamLeagueListId +
                ", awayTeamLeagueListId=" + awayTeamLeagueListId +
                ", homeTeamScore=" + homeTeamScore +
                ", awayTeamScore=" + awayTeamScore +
                ", matchStatus='" + matchStatus + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}

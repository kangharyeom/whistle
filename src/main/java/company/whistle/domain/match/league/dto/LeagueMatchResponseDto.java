package company.whistle.domain.match.league.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class LeagueMatchResponseDto {
    private Long leagueMatchId;
    private Long homeTeamUserId;
    private Long awayTeamUserId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long homeTeamLeagueListId;
    private Long awayTeamLeagueListId;
    private Long homeTeamScore;
    private Long homeTeamHonorScore;
    private Long awayTeamScore;
    private Long awayTeamHonorScore;
    private String homeTeamName;
    private String awayTeamName;
    private String homeTeamManagerName;
    private String awayTeamManagerName;
    private Long homeTeamLeagueMatchPoints;
    private Long awayTeamLeagueMatchPoints;
    private Long homeTeamLeagueWinRecord;
    private Long awayTeamLeagueWinRecord;
    private Long homeTeamLeagueDrawRecord;
    private Long awayTeamLeagueDrawRecord;
    private Long homeTeamLeagueLoseRecord;
    private Long awayTeamLeagueLoseRecord;
    private Long homeTeamTotalWinRecord;
    private Long awayTeamTotalWinRecord;
    private Long homeTeamTotalDrawRecord;
    private Long awayTeamTotalDrawRecord;
    private Long homeTeamTotalLoseRecord;
    private Long awayTeamTotalLoseRecord;
    private String homeTeamLevelType;
    private String awayTeamLevelType;
    private String homeTeamAgeType;
    private String awayTeamAgeType;
    private String homeTeamUniformType;
    private String awayTeamUniformType;
    private String matchType;
    private String sportType;
    private String locationType;
    private String matchTime;
    private String matchStatus;
    private String matchRules;
    private String homeTeamMatchResultStatus;
    private String awayTeamMatchResultStatus;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Override
    public String toString() {
        return "LeagueMatchResponseDto{" +
                "leagueMatchId=" + leagueMatchId +
                ", homeTeamUserId=" + homeTeamUserId +
                ", awayTeamUserId=" + awayTeamUserId +
                ", homeTeamId=" + homeTeamId +
                ", awayTeamId=" + awayTeamId +
                ", homeTeamLeagueListId=" + homeTeamLeagueListId +
                ", awayTeamLeagueListId=" + awayTeamLeagueListId +
                ", homeTeamScore=" + homeTeamScore +
                ", homeTeamHonorScore=" + homeTeamHonorScore +
                ", awayTeamScore=" + awayTeamScore +
                ", awayTeamHonorScore=" + awayTeamHonorScore +
                ", homeTeamName='" + homeTeamName + '\'' +
                ", awayTeamName='" + awayTeamName + '\'' +
                ", homeTeamManagerName='" + homeTeamManagerName + '\'' +
                ", awayTeamManagerName='" + awayTeamManagerName + '\'' +
                ", homeTeamLeagueMatchPoints=" + homeTeamLeagueMatchPoints +
                ", awayTeamLeagueMatchPoints=" + awayTeamLeagueMatchPoints +
                ", homeTeamLeagueWinRecord=" + homeTeamLeagueWinRecord +
                ", awayTeamLeagueWinRecord=" + awayTeamLeagueWinRecord +
                ", homeTeamLeagueDrawRecord=" + homeTeamLeagueDrawRecord +
                ", awayTeamLeagueDrawRecord=" + awayTeamLeagueDrawRecord +
                ", homeTeamLeagueLoseRecord=" + homeTeamLeagueLoseRecord +
                ", awayTeamLeagueLoseRecord=" + awayTeamLeagueLoseRecord +
                ", homeTeamTotalWinRecord=" + homeTeamTotalWinRecord +
                ", awayTeamTotalWinRecord=" + awayTeamTotalWinRecord +
                ", homeTeamTotalDrawRecord=" + homeTeamTotalDrawRecord +
                ", awayTeamTotalDrawRecord=" + awayTeamTotalDrawRecord +
                ", homeTeamTotalLoseRecord=" + homeTeamTotalLoseRecord +
                ", awayTeamTotalLoseRecord=" + awayTeamTotalLoseRecord +
                ", homeTeamLevelType='" + homeTeamLevelType + '\'' +
                ", awayTeamLevelType='" + awayTeamLevelType + '\'' +
                ", homeTeamAgeType='" + homeTeamAgeType + '\'' +
                ", awayTeamAgeType='" + awayTeamAgeType + '\'' +
                ", homeTeamUniformType='" + homeTeamUniformType + '\'' +
                ", awayTeamUniformType='" + awayTeamUniformType + '\'' +
                ", matchType='" + matchType + '\'' +
                ", sportType='" + sportType + '\'' +
                ", locationType='" + locationType + '\'' +
                ", matchTime='" + matchTime + '\'' +
                ", matchStatus='" + matchStatus + '\'' +
                ", matchRules='" + matchRules + '\'' +
                ", homeTeamMatchResultStatus='" + homeTeamMatchResultStatus + '\'' +
                ", awayTeamMatchResultStatus='" + awayTeamMatchResultStatus + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}

package company.whistle.domain.match.unrank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class MatchResponseDto {
    private Long matchId;
    private Long userId;
    private Long teamId;
    private Long homeTeamHonorScore;
    private String homeTeamName;
    private String homeTeamManagerName;
    private Long homeTeamTotalWinRecord;
    private Long homeTeamTotalDrawRecord;
    private Long homeTeamTotalLoseRecord;
    private String homeTeamLevelType;
    private String homeTeamAgeType;
    private String homeTeamUniformType;
    private String matchType;
    private String sportsType;
    private String locationType;
    private String matchTime;
    private String matchDate;
    private String matchStatus;
    private String matchRules;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Override
    public String toString() {
        return "MatchResponseDto{" +
                "matchId=" + matchId +
                ", userId=" + userId +
                ", teamId=" + teamId +
                ", homeTeamHonorScore=" + homeTeamHonorScore +
                ", homeTeamName='" + homeTeamName + '\'' +
                ", homeTeamManagerName='" + homeTeamManagerName + '\'' +
                ", homeTeamTotalWinRecord=" + homeTeamTotalWinRecord +
                ", homeTeamTotalDrawRecord=" + homeTeamTotalDrawRecord +
                ", homeTeamTotalLoseRecord=" + homeTeamTotalLoseRecord +
                ", homeTeamLevelType='" + homeTeamLevelType + '\'' +
                ", homeTeamAgeType='" + homeTeamAgeType + '\'' +
                ", homeTeamUniformType='" + homeTeamUniformType + '\'' +
                ", matchType='" + matchType + '\'' +
                ", sportsType='" + sportsType + '\'' +
                ", locationType='" + locationType + '\'' +
                ", matchTime='" + matchTime + '\'' +
                ", matchDate='" + matchDate + '\'' +
                ", matchStatus='" + matchStatus + '\'' +
                ", matchRules='" + matchRules + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}

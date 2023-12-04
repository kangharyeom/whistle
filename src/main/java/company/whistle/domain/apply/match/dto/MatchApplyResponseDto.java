package company.whistle.domain.apply.match.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class MatchApplyResponseDto {
    private Long userId;
    private Long teamId;
    private Long leagueId;
    private Long matchId;
    private Long matchApplyId;
    private String managerName;
    private String teamName;
    private String levelType;
    private String ageType;
    private String applyType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Override
    public String toString() {
        return "MatchApplyResponseDto{" +
                "userId=" + userId +
                ", teamId=" + teamId +
                ", leagueId=" + leagueId +
                ", matchId=" + matchId +
                ", matchApplyId=" + matchApplyId +
                ", managerName='" + managerName + '\'' +
                ", teamName='" + teamName + '\'' +
                ", levelType='" + levelType + '\'' +
                ", ageType='" + ageType + '\'' +
                ", applyType='" + applyType + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}

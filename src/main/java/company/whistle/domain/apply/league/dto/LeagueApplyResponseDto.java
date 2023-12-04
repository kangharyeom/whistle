package company.whistle.domain.apply.league.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class LeagueApplyResponseDto {
    private Long userId;
    private Long teamId;
    private Long leagueId;
    private Long leagueApplyId;
    private String managerName;
    private String teamName;
    private String levelType;
    private String ageType;
    private String applyType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Override
    public String toString() {
        return "LeagueApplyResponseDto{" +
                "userId=" + userId +
                ", teamId=" + teamId +
                ", leagueId=" + leagueId +
                ", leagueApplyId=" + leagueApplyId +
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

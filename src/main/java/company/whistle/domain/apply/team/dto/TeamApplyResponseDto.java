package company.whistle.domain.apply.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class TeamApplyResponseDto {
    private Long teamApplyId;
    private Long userId;
    private Long teamId;
    private String managerName;
    private String teamName;
    private String levelType;
    private String ageType;
    private String applyType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Override
    public String toString() {
        return "TeamApplyResponseDto{" +
                "teamApplyId=" + teamApplyId +
                ", userId=" + userId +
                ", teamId=" + teamId +
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

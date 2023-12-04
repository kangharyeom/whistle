package company.whistle.domain.apply.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeamApplyPostDto {
    private Long userId;
    private Long teamId;
    private Long leagueId;
    private Long matchId;
    private String managerName;
    private String teamName;
    private String levelType;
    private String ageType;
    private String applyType;
}

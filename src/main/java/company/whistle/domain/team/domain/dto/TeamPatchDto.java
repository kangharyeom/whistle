package company.whistle.domain.team.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeamPatchDto {
    private Long teamId;
    private Long userId;
    private Long championCount;
    private Long memberCount;
    private Long leagueMatchPoints;
    private Long leagueMatchCount;
    private Long leagueWinRecord;
    private Long leagueDrawRecord;
    private Long leagueLoseRecord;
    private Long totalMatchCount;
    private Long totalWinRecord;
    private Long totalDrawRecord;
    private Long totalLoseRecord;
    private Long honorScore;
    private Long mostGoals;
    private Long mostAssist;
    private Long mostMom;

    private String formation;
    private String introduction;
    @NotBlank(message = "연령대를 입력 해야 합니다.")
    private String ageType;
    @NotBlank(message = "지역을 입력 해야 합니다.")
    private String locationType;
    @NotBlank(message = "운동 유형을 입력 해야 합니다.")
    private String sportsType;
    @NotBlank(message = "팀 실력을 입력 해야 합니다.")
    private String levelType;
    private String frequency;
    private String managerName;
    private String subManagerName;
    private String uniformType;
    private String leagueName;
}

package company.whistle.domain.league.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeaguePostDto {
    private Long userId;
    private Long teamId;
    private Long memberCount;
    private Long matchCount;
    private Long teamCount = 1L;
    private Long leagueEndCount = 0L;
    private Long honorScore;
    private String teamName;
    private Long teamGoals = 0L;
    private Long teamAssist = 0L;
    private Long cleanSheet = 0L;
    private String managerName;
    private String subManagerName;
    private String managerTeamName;
    @NotBlank(message = "리그의 이름을 입력 해야 합니다.")
    private String leagueName;
    @NotBlank(message = "운동의 유형을 입력 해야 합니다.")
    private String sportsType;
    @NotBlank(message = "연령대를 입력 해야 합니다.")
    private String ageType;
    @NotBlank(message = "지역을 입력 해야 합니다.")
    private String locationType;
    @NotBlank(message = "리그 경기 기간을 입력 해야 합니다.")
    private String period;
    @NotBlank(message = "리그의 실력을 입력 해야 합니다.")
    private String levelType;
    private String leagueRules;
    @NotBlank(message = "리그의 선호 경기 빈도를 입력 해야 합니다.")
    private String frequency;
    private String seasonType="TEAM_RECRUIT";
    private String title;
    private String content;

}

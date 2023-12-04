package company.whistle.domain.team.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeamPostDto {
    private Long userId;

    @NotBlank(message = "팀 이름을 입력 해야 합니다.")
    private String teamName;
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
    private String formation;

    private Long championCount = 0L ;
    private Long memberCount = 1L ;
    private Long leagueMatchPoints = 0L;
    private Long leagueMatchCount = 0L;
    private Long leagueWinRecord = 0L ;
    private Long leagueDrawRecord = 0L ;
    private Long leagueLoseRecord = 0L ;
    private Long totalMatchCount = 0L;
    private Long totalWinRecord = 0L ;
    private Long totalDrawRecord = 0L ;
    private Long totalLoseRecord = 0L ;
    private Long honorScore = 0L ;
    private Long mostGoals = 0L ;
    private Long mostAssist = 0L ;
    private Long mostMom = 0L ;

}

package company.whistle.domain.league.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeaguePatchDto {
    private Long leagueId;
    private Long userId;
    private Long teamId;
    private Long matchCount;
    private Long memberCount;
    private Long honorScore;
    private Long winPoints;
    @NotBlank(message = "리그의 이름을 입력 해야 합니다.")
    private String leagueName;
    @NotBlank(message = "운동의 유형을 입력 해야 합니다.")
    private String sportsType;
    private Long teamCount;
    @NotBlank(message = "연령대를 입력 해야 합니다.")
    private String ageType;
    @NotBlank(message = "지역을 입력 해야 합니다.")
    private String locationType;
    @NotBlank(message = "리그 경기 기간을 입력 해야 합니다.")
    private String period;
    @NotBlank(message = "리그의 난이도를 입력 해야 합니다.")
    private String levelType;
    private String leagueRules;
    @NotBlank(message = "리그의 경기 빈도를 입력 해야 합니다.")
    private String frequency;
    private String seasonType;

    public void updateId(Long id){
        this.leagueId = id;
    }
}

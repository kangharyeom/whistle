package company.whistle.domain.match.league.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeagueMatchPatchDto {
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
    @NotBlank(message = "운동의 유형을 입력 해야 합니다.")
    private String matchType;
    @NotBlank(message = "운동의 유형을 입력 해야 합니다.")
    private String sportType;
    @NotBlank(message = "연령대를 입력 해야 합니다.")
    private String ageType;
    @NotBlank(message = "지역을 입력 해야 합니다.")
    private String locationType;
    @NotBlank(message = "경기 시간을 입력 해야 합니다.")
    private String matchTime;
    @NotBlank(message = "경기의 난이도를 입력 해야 합니다.")
    private String levelType;
    private String matchStatus;
    private String homeTeamMatchResultStatus;
    private String awayTeamMatchResultStatus;
    private String matchRules;


    public void updateId(Long id){
        this.leagueMatchId = id;
    }
}

package company.whistle.domain.match.league.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeagueMatchPostDto {
    private Long homeTeamUserId;
    private Long awayTeamUserId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long homeTeamLeagueListId;
    private Long awayTeamLeagueListId;
    private Long homeTeamScore = 0L;
    private Long awayTeamScore = 0L;
    private Long homeTeamHonorScore;
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
    private String locationType;
    private String sportType;
    private String matchType = "LEAGUE";
    @NotBlank(message = "경기 시간을 입력 해야 합니다.")
    private String matchTime;
    private String matchStatus = "BEFORE";
    private String homeTeamMatchResultStatus = "NONE";
    private String awayTeamMatchResultStatus = "NONE";
    private String matchRules;
}

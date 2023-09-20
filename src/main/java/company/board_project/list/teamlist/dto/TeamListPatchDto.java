package company.board_project.list.teamlist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeamListPatchDto {
    private Long teamListId;
    private Long userId;
    private Long teamId;
    private Long leagueId;
    private Long applyId;
    private Long honorScore;
    private Long memberCount;
    private String position;
    private String subManagerName;
    private String ageType;
    private String locationType;
    private String levelType;
    private String leagueRules;
    private String frequency;
    private String uniformType;
    private Long leagueMatchPoints;
    private Long leagueWinRecord;
    private Long leagueDrawRecord;
    private Long leagueLoseRecord;
    private Long ranking;
    private Long teamGoals;
    private Long teamAssist;
    private Long cleanSheet;
}
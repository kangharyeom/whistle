package company.board_project.list.matchlist.entity;

import company.board_project.apply.entity.Apply;
import company.board_project.audit.Auditable;
import company.board_project.constant.*;
import company.board_project.match.entity.Match;
import company.board_project.team.entity.Team;
import company.board_project.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "MATCH_LISTS")
public class MatchList extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchListId;

    @Column
    private Long homeTeamScore;

    @Column
    private Long awayTeamScore;

    @Column
    private Long homeTeamHonorScore;

    @Column
    private Long awayTeamHonorScore;

    @Column
    private String homeTeamName;

    @Column
    private String awayTeamName;

    @Column
    private String homeTeamManagerName;

    @Column
    private String awayTeamManagerName;

    @Column
    private Long homeTeamLeagueMatchPoints;

    @Column
    private Long awayTeamLeagueMatchPoints;

    @Column
    private Long homeTeamLeagueWinRecord;

    @Column
    private Long awayTeamLeagueWinRecord;

    @Column
    private Long homeTeamLeagueDrawRecord;

    @Column
    private Long awayTeamLeagueDrawRecord;

    @Column
    private Long homeTeamLeagueLoseRecord;

    @Column
    private Long awayTeamLeagueLoseRecord;

    @Column
    private Long homeTeamTotalWinRecord;

    @Column
    private Long awayTeamTotalWinRecord;

    @Column
    private Long homeTeamTotalDrawRecord;

    @Column
    private Long awayTeamTotalDrawRecord;

    @Column
    private Long homeTeamTotalLoseRecord;

    @Column
    private Long awayTeamTotalLoseRecord;

    @Column
    private Long homeTeamRanking;

    @Column
    private Long awayTeamRanking;

    @Enumerated(EnumType.STRING)
    private LevelType homeTeamLevelType;

    @Enumerated(EnumType.STRING)
    private LevelType awayTeamLevelType;

    @Enumerated(EnumType.STRING)
    private AgeType homeTeamAgeType;

    @Enumerated(EnumType.STRING)
    private AgeType awayTeamAgeType;

    @Enumerated(EnumType.STRING)
    private UniformType homeTeamUniformType;

    @Enumerated(EnumType.STRING)
    private UniformType awayTeamUniformType;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "APPLY_ID")
    private Apply apply;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "MATCH_ID")
    private Match match;

}
package company.board_project.leaguematch.entity;

import company.board_project.apply.entity.Apply;
import company.board_project.audit.Auditable;
import company.board_project.constant.*;
import company.board_project.league.entity.League;
import company.board_project.list.leaguelist.entity.LeagueList;
import company.board_project.list.matchlist.entity.MatchList;
import company.board_project.schedule.entity.Schedule;
import company.board_project.team.entity.Team;
import company.board_project.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "LEAGUE_MATCHES")
public class LeagueMatch extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leagueMatchId;

    @Column
    private Long homeTeamScore;

    @Column
    private Long homeTeamHonorScore;

    @Column
    private Long awayTeamScore;

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

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    @Enumerated(EnumType.STRING)
    private SportsType sportType;

    @Column(nullable = false)
    private String matchTime;

    @Column
    private String matchRules;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus = MatchStatus.BEFORE;

    @Enumerated(EnumType.STRING)
    private MatchResultStatus homeTeamMatchResultStatus = MatchResultStatus.NONE;

    @Enumerated(EnumType.STRING)
    private MatchResultStatus awayTeamMatchResultStatus = MatchResultStatus.NONE;

    @OneToMany(mappedBy = "match", cascade = CascadeType.REMOVE)
    private List<Apply> applies = new ArrayList<>();

    @OneToMany(mappedBy = "match", cascade = CascadeType.REMOVE)
    private List<MatchList> matchLists = new ArrayList<>();

//    @OneToMany(mappedBy = "match", cascade = CascadeType.REMOVE)
//    private List<LeagueList> leagueList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "LEAGUE_ID")
    private League league;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "LEAGUE_LIST_ID")
    private LeagueList leagueList;

}
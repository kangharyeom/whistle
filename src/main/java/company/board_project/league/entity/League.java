package company.board_project.league.entity;

import company.board_project.audit.Auditable;
import company.board_project.constant.*;
import company.board_project.content.entity.Content;
import company.board_project.list.leaguelist.entity.LeagueList;
import company.board_project.list.matchlist.entity.MatchList;
import company.board_project.list.teamlist.entity.TeamList;
import company.board_project.match.entity.Match;
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
@Table(name = "LEAGUES")
public class League extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leagueId;

    @Column(nullable = false)
    private Long matchCount;

    @Column
    private Long teamCount;

    @Column(nullable = false)
    private String leagueName;

    @Column(nullable = false)
    private String managerName;

    @Column
    private String participantTeamName;

    @Enumerated(EnumType.STRING)
    private SportsType sportsType;

    @Enumerated(EnumType.STRING)
    private AgeType ageType;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Column(nullable = false)
    private String period;

    @Enumerated(EnumType.STRING)
    private LevelType levelType;

    @Column
    private String leagueRules;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Column
    private Long memberCount;

    @Column
    private Long leagueWinRecord;

    @Column
    private Long leagueDrawRecord;

    @Column
    private Long leagueLoseRecord;

    @Column
    private Long honorScore;

    @Column
    private Long winPoints;

    @OneToMany(mappedBy = "league", cascade = CascadeType.REMOVE)
    private List<Match> matches = new ArrayList<>();

    @OneToMany(mappedBy = "league", cascade = CascadeType.REMOVE)
    private List<Content> contents = new ArrayList<>();

    @OneToMany(mappedBy = "league", cascade = CascadeType.REMOVE)
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "league", cascade = CascadeType.REMOVE)
    private List<LeagueList> leagueLists = new ArrayList<>();

    @OneToMany(mappedBy = "league", cascade = CascadeType.REMOVE)
    private List<MatchList> matchLists = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "TEAM_LIST_ID")
//    private LeagueList teamList;

}



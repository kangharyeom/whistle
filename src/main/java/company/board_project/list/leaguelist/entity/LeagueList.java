package company.board_project.list.leaguelist.entity;

import company.board_project.apply.entity.Apply;
import company.board_project.audit.Auditable;
import company.board_project.constant.*;
import company.board_project.league.entity.League;
import company.board_project.list.matchlist.entity.MatchList;
import company.board_project.match.entity.Match;
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
@Table(name = "LEAGUE_LISTS")
public class LeagueList extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leagueListId;

    @Column
    private Long leagueHonorScore;

    @Column
    private Long honorScore;

    @Column
    private Long memberCount;

    @Column
    private Long championCount;

    @Column
    private String teamName;

    @Column
    private String managerName;

    @Column
    private String subManagerName;
    
    @Column
    private String leagueName;

    @Enumerated(EnumType.STRING)
    private Formation formation;
    
    @Enumerated(EnumType.STRING)
    private AgeType ageType;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Enumerated(EnumType.STRING)
    private LevelType levelType;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Enumerated(EnumType.STRING)
    private UniformType uniformType;

    @Column
    private Long leagueMatchCount;

    @Column
    private Long leagueMatchPoints;

    @Column
    private Long leagueWinRecord;

    @Column
    private Long leagueDrawRecord;

    @Column
    private Long leagueLoseRecord;

    @Column
    private Long teamGoals;

    @Column
    private Long teamAssist;

    @Column
    private Long cleanSheet;

    /*@Column
    private Long mostGoals;
    @Column
    private Long mostAssists;
    @Column
    private Long mostMoMs;*/

    @OneToMany(mappedBy = "leagueList", cascade = CascadeType.REMOVE)
    private List<MatchList> matchLists = new ArrayList<>();

    @OneToMany(mappedBy = "leagueList", cascade = CascadeType.REMOVE)
    private List<Match> matches = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "LEAGUE_ID")
    private League league;

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

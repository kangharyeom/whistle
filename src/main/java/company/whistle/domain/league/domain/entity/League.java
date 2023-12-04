package company.whistle.domain.league.domain.entity;

import company.whistle.domain.league.participants.entity.Participants;
import company.whistle.global.audit.Auditable;
import company.whistle.domain.board.content.entity.Content;
import company.whistle.global.constant.*;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.user.entity.User;
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

    // matchCount == leagueEndCount 일경우 리그 종료
    // leagueEndCount == (각 팀 경기수 총합/팀 수)
    @Column
    private Long leagueEndCount;

    @Column
    private Long teamCount;

    @Column
    private Long memberCount;

    @Column(nullable = false)
    private String leagueName;

    @Column(nullable = false)
    private String managerName;

    @Column
    private String managerTeamName;

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

    @Enumerated(EnumType.STRING)
    private SeasonType seasonType;

    @Column
    private Long honorScore;

    @OneToMany(mappedBy = "league", cascade = CascadeType.REMOVE)
    private List<Content> contents = new ArrayList<>();

    @OneToMany(mappedBy = "league", cascade = CascadeType.REMOVE)
    private List<Participants> participants = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

}



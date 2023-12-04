package company.whistle.domain.match.unrank.entity;

import company.whistle.domain.apply.match.entity.MatchApply;
import company.whistle.global.audit.Auditable;
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
@Table(name = "MATCHES")
public class Match extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @Column
    private Long homeTeamUserId;

    @Column
    private Long homeTeamId;

    @Enumerated(EnumType.STRING)
    private UniformType homeTeamUniformType;

    @Column
    private String homeTeamName;

    @Column
    private String homeTeamManagerName;

    @Column
    private Long homeTeamHonorScore;

    @Column
    private Long homeTeamTotalWinRecord;

    @Column
    private Long homeTeamTotalDrawRecord;

    @Column
    private Long homeTeamTotalLoseRecord;

    @Enumerated(EnumType.STRING)
    private LevelType homeTeamLevelType;

    @Enumerated(EnumType.STRING)
    private AgeType homeTeamAgeType;



    @Column
    private Long awayTeamHonorScore;

    @Column
    private String awayTeamName;

    @Column
    private String awayTeamManagerName;

    @Column
    private Long awayTeamTotalWinRecord;

    @Column
    private Long awayTeamTotalDrawRecord;

    @Column
    private Long awayTeamTotalLoseRecord;

    @Enumerated(EnumType.STRING)
    private LevelType awayTeamLevelType;

    @Enumerated(EnumType.STRING)
    private AgeType awayTeamAgeType;

    @Enumerated(EnumType.STRING)
    private UniformType awayTeamUniformType;

    @Enumerated(EnumType.STRING)
    private SportsType sportsType;

    @Column
    private String matchRules;

    @Column
    private String matchTime;

    @Column
    private String matchDate;

    // userInput

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    // DB Input

    @Column
    private Long awayTeamUserId;

    @Column
    private Long awayTeamId;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    @Column
    private Long homeTeamScore = 0L;

    @Column
    private Long awayTeamScore = 0L;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus = MatchStatus.RIVAL_RECRUIT;

    @Enumerated(EnumType.STRING)
    private MatchResultStatus homeTeamMatchResultStatus = MatchResultStatus.NONE;

    @Enumerated(EnumType.STRING)
    private MatchResultStatus awayTeamMatchResultStatus = MatchResultStatus.NONE;

    @OneToMany(mappedBy = "match", cascade = CascadeType.REMOVE)
    private List<MatchApply> matchApplies = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

}

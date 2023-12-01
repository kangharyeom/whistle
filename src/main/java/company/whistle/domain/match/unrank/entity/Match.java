package company.whistle.domain.match.unrank.entity;

import company.whistle.domain.apply.match.entity.MatchApply;
import company.whistle.domain.match.schedule.entity.Schedule;
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

    private Long applyId;

    @Column
    private Long homeTeamHonorScore;

    @Column
    private String homeTeamName;

    @Column
    private String homeTeamManagerName;

    @Column
    private Long homeTeamTotalWinRecord;

    @Column
    private Long homeTeamTotalDrawRecord;

    @Column
    private Long homeTeamTotalLoseRecord;

    @Column
    private Long homeTeamScore = 0L;

    @Enumerated(EnumType.STRING)
    private LevelType homeTeamLevelType;

    @Enumerated(EnumType.STRING)
    private AgeType homeTeamAgeType;

    @Enumerated(EnumType.STRING)
    private UniformType homeTeamUniformType;






    @Column
    private Long awayTeamUserId;

    @Column
    private Long awayTeamId;

    @Column
    private Long awayTeamScore = 0L;

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
    private LocationType locationType;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    @Enumerated(EnumType.STRING)
    private SportsType sportsType;

    @Column
    private String matchRules;

    @Column
    private String matchTime;

    @Column
    private String matchDate;

    // DB Input

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus = MatchStatus.RIVAL_RECRUIT;

    @Enumerated(EnumType.STRING)
    private MatchResultStatus homeTeamMatchResultStatus = MatchResultStatus.NONE;

    @Enumerated(EnumType.STRING)
    private MatchResultStatus awayTeamMatchResultStatus = MatchResultStatus.NONE;

    @OneToMany(mappedBy = "match", cascade = CascadeType.REMOVE)
    private List<MatchApply> matchApplies = new ArrayList<>();

    @OneToMany(mappedBy = "match", cascade = CascadeType.REMOVE)
    private List<Schedule> schedule = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

}

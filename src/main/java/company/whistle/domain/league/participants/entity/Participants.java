package company.whistle.domain.league.participants.entity;

import company.whistle.domain.apply.league.entity.LeagueApply;
import company.whistle.global.audit.Auditable;
import company.whistle.global.constant.*;
import company.whistle.domain.league.domain.entity.League;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PARTICIPANTS")
public class Participants extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantsId;

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
    @JoinColumn(name = "LEAGUE_APPLY_ID")
    private LeagueApply leagueApply;

}

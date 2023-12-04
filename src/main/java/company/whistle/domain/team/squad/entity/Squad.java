package company.whistle.domain.team.squad.entity;

import company.whistle.domain.apply.team.entity.TeamApply;
import company.whistle.global.audit.Auditable;
import company.whistle.global.constant.*;
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
@Table(name = "SQUADS")
public class Squad extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long squadId;
    
    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private TeamMemberRole teamMemberRole;

    @Enumerated(EnumType.STRING)
    private AgeType ageType;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Enumerated(EnumType.STRING)
    private LevelType levelType;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

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
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "TEAM_APPLY_ID")
    private TeamApply teamApply;

}

package company.whistle.domain.apply.team.entity;

import company.whistle.domain.team.squad.entity.Squad;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.user.entity.User;
import company.whistle.global.audit.Auditable;
import company.whistle.global.constant.AgeType;
import company.whistle.global.constant.ApplyType;
import company.whistle.global.constant.LevelType;
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
@Table(name = "TEAM_APPLIES")
public class TeamApply extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamApplyId;

    @Column(nullable = false)
    private String managerName;

    private String teamName;

    @Enumerated(EnumType.STRING)
    private LevelType levelType;

    @Enumerated(EnumType.STRING)
    private AgeType ageType;

    @Enumerated(EnumType.STRING)
    private ApplyType applyType;

    @OneToMany(mappedBy = "teamApply", cascade = CascadeType.REMOVE)
    private List<Squad> squads = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

}

package company.whistle.domain.user.entity;

import company.whistle.domain.apply.league.entity.LeagueApply;
import company.whistle.domain.apply.match.entity.MatchApply;
import company.whistle.domain.apply.team.entity.TeamApply;
import company.whistle.domain.league.participants.entity.Participants;
import company.whistle.domain.team.squad.entity.Squad;
import company.whistle.global.audit.Auditable;
import company.whistle.domain.board.comment.entity.Comment;
import company.whistle.global.constant.*;
import company.whistle.domain.board.content.entity.Content;
import company.whistle.domain.league.domain.entity.League;
import company.whistle.domain.match.unrank.entity.Match;
import company.whistle.domain.team.domain.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, updatable = false)
    private String loginId;

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    @Enumerated(EnumType.STRING)
    private LeagueRole leagueRole;

    @Enumerated(EnumType.STRING)
    private TeamMemberRole teamMemberRole;

    @Column
    @Enumerated(EnumType.STRING)
    private LoginType loginType = LoginType.BASIC;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Content> contents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Match> matches = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<League> leagues = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<TeamApply> teamApplies = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<MatchApply> matchApplies = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<LeagueApply> leagueApplies = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Squad> squads = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Participants> participants = new ArrayList<>();

}
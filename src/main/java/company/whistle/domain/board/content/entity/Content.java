package company.whistle.domain.board.content.entity;

import company.whistle.global.audit.Auditable;
import company.whistle.domain.board.comment.entity.Comment;
import company.whistle.global.constant.CategoryType;
import company.whistle.domain.league.domain.entity.League;
import company.whistle.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "CONTENTS")
public class Content extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENT_ID")
    private Long contentId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "LEAGUE_ID")
    private League league;

    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

}

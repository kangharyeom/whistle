package company.board_project.apply.service;

import company.board_project.constant.MatchResultStatus;
import company.board_project.exception.BusinessLogicException;
import company.board_project.exception.Exceptions;
import company.board_project.apply.entity.Apply;
import company.board_project.apply.repository.ApplyRepository;
import company.board_project.league.entity.League;
import company.board_project.league.repository.LeagueRepository;
import company.board_project.league.service.LeagueService;
import company.board_project.match.entity.Match;
import company.board_project.match.repository.MatchRepository;
import company.board_project.match.service.MatchService;
import company.board_project.team.entity.Team;
import company.board_project.team.repository.TeamRepository;
import company.board_project.team.service.TeamService;
import company.board_project.user.entity.User;
import company.board_project.user.repository.UserRepository;
import company.board_project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TeamService teamService;
    private final TeamRepository teamRepository;
    private final MatchService matchService;
    private final MatchRepository matchRepository;
    private final LeagueService leagueService;
    private final LeagueRepository leagueRepository;

    public Apply createApply(Apply apply, Long userId, Long teamId) {
        User user = userService.findUser(userId);
        Team team = teamService.findTeam(teamId);

        apply.setTeam(team);
        apply.setUser(user);

        userRepository.save(user);
        teamRepository.save(team);
        applyRepository.save(apply);

        return apply;
    }

    public Apply createTeamApply(Apply apply, Long userId, Long teamId) {
        User user = userService.findUser(userId);
        Team team = teamService.findTeam(teamId);

        apply.setTeam(team);
        apply.setUser(user);
        apply.setManagerName(user.getName());
        user.setUserTeamApplyId(team.getTeamId());
        apply.setUserTeamApplyId(team.getTeamId());

        userRepository.save(user);
        teamRepository.save(team);
        applyRepository.save(apply);

        return apply;
    }

    public Apply createMatchApply(Apply apply, Long userId, Long matchId) {
        User user = userService.findUser(userId);
        Match match = matchService.findMatch(matchId);

        apply.setUser(user);
        apply.setManagerName(user.getName());
        user.setUserMatchApplyId(match.getMatchId());
        apply.setUserMatchApplyId(match.getMatchId());

        userRepository.save(user);
        matchRepository.save(match);
        applyRepository.save(apply);

        return apply;
    }

    public Apply createLeagueApply(Apply apply, Long userId, Long leagueId, Long teamId) {
        User user = userService.findUser(userId);
        League league = leagueService.findLeague(leagueId);
        Team team = teamService.findTeam(teamId);

        apply.setLeague(league);
        apply.setUser(user);
        apply.setManagerName(user.getName());
        apply.setTeamName(team.getTeamName());
        user.setUserLeagueApplyId(league.getLeagueId());
        apply.setUserLeagueApplyId(league.getLeagueId());

        userRepository.save(user);
        leagueRepository.save(league);
        applyRepository.save(apply);

        return apply;
    }

    public Apply findApply(Long applyId) {
        return findVerifiedApply(applyId);
    }

    public List<Apply> findAllByTeamId(Long teamId){
        return applyRepository.findAllByTeamId(teamId);
    }

    public List<Apply> findAllByMatchId(Long matchId){
        return applyRepository.findAllByMatchId(matchId);
    }

    public List<Apply> findAllByLeagueId(Long leagueId){
        return applyRepository.findAllByLeagueId(leagueId);
    }

    public void deleteApply(Long applyId) {
        Apply findApply = findVerifiedApply(applyId);

        applyRepository.delete(findApply);
    }

    public User findVerifiedUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User findUser =
                optionalUser.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.USER_NOT_FOUND));
        return findUser;
    }

    public Apply findVerifiedApply(Long applyId) {
        Optional<Apply> optionalApply = applyRepository.findById(applyId);

        Apply findApply =
                optionalApply.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.CONTENT_NOT_FOUND));

        return findApply;
    }
}

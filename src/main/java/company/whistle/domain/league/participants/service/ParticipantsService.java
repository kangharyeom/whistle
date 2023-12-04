package company.whistle.domain.league.participants.service;

import company.whistle.domain.apply.league.entity.LeagueApply;
import company.whistle.domain.apply.league.service.LeagueApplyService;
import company.whistle.domain.league.participants.entity.Participants;
import company.whistle.domain.league.participants.repository.ParticipantsRepository;
import company.whistle.domain.user.repository.UserRepository;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.domain.league.domain.entity.League;
import company.whistle.domain.league.domain.repository.LeagueRepository;
import company.whistle.domain.league.domain.service.LeagueService;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.team.domain.service.TeamService;
import company.whistle.domain.user.entity.User;
import company.whistle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ParticipantsService {
    private final ParticipantsRepository participantsRepository;
    private final TeamService teamService;
    private final LeagueService leagueService;
    private final UserService userService;
    private final LeagueApplyService leagueApplyService;
    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;
    public Participants createParticipants(
            Participants participants, Long userId, Long teamId, Long leagueId, Long leagueApplyId) {
        try {
            if (userId == null || teamId == null || leagueId == null || leagueApplyId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            User user = userService.findUser(userId);
            Team team = teamService.findTeam(teamId);
            LeagueApply leagueApply = leagueApplyService.findLeagueApply(leagueApplyId);
            League league = leagueService.findLeague(leagueId);

            participants.setUser(user);
            participants.setTeam(team);
            participants.setLeagueApply(leagueApply);
            participants.setLeague(league);

            participants.setManagerName(user.getName());

            participants.setHonorScore(team.getHonorScore());
            participants.setChampionCount(team.getChampionCount());
            participants.setMemberCount(team.getMemberCount());
            participants.setTeamName(team.getTeamName());
            participants.setSubManagerName(team.getSubManagerName());

            participants.setLeagueName(league.getLeagueName());

            participants.setTeamAssist(participants.getTeamAssist());
            participants.setTeamGoals(participants.getTeamGoals());
            participants.setLeagueHonorScore(participants.getLeagueHonorScore());
            participants.setLeagueMatchCount(participants.getLeagueMatchCount());

            //비즈니스 로직
            league.setTeamCount(+1L);
            league.setMemberCount(team.getMemberCount()+ participants.getMemberCount());
            leagueRepository.save(league);
            participantsRepository.save(participants);
        } catch (BusinessLogicException e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(e.getExceptions());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.PARTICIPANTS_NOT_CREATED);
        }
        return participants;
    }

    public Participants createParticipantsByLeagueController(
            Participants participants, Long userId, Long teamId, Long leagueId) {
        try {
            if (userId == null || teamId == null || leagueId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            User user = userService.findUser(userId);
            Team team = teamService.findTeam(teamId);

            League league = leagueService.findLeague(leagueId);

            participants.setUser(user);
            participants.setTeam(team);
            participants.setLeague(league);

            participants.setLeagueName(league.getLeagueName());
            participants.setLeagueWinRecord(0L);
            participants.setLeagueLoseRecord(0L);
            participants.setLeagueDrawRecord(0L);
            participants.setLeagueMatchPoints(0L);
            participants.setLeagueMatchCount(0L);

            participants.setChampionCount(team.getChampionCount());
            participants.setFormation(team.getFormation());
            participants.setHonorScore(team.getHonorScore());
            participants.setLeagueHonorScore(team.getHonorScore());
            participants.setMemberCount(team.getMemberCount());
            participants.setSubManagerName(team.getSubManagerName());
            participants.setTeamName(team.getTeamName());
            participants.setUniformType(team.getUniformType());

            participants.setManagerName(user.getName());

            userRepository.save(user);
            participantsRepository.save(participants);
        } catch (BusinessLogicException e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(e.getExceptions());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.PARTICIPANTS_NOT_CREATED);
        }
        return participants;
    }

    public Participants updateParticipants(
            Participants participants,
            Long participantsId) {
        try {
            if (participantsId == null ) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Participants findParticipants = findVerifiedParticipants(participantsId);

            Optional.ofNullable(participants.getFormation())
                    .ifPresent(findParticipants::setFormation);

            Optional.ofNullable(participants.getChampionCount())
                    .ifPresent(findParticipants::setChampionCount);

            Optional.ofNullable(participants.getMemberCount())
                    .ifPresent(findParticipants::setMemberCount);

            Optional.ofNullable(participants.getLeagueMatchCount())
                    .ifPresent(findParticipants::setLeagueMatchCount);

            Optional.ofNullable(participants.getLeagueWinRecord())
                    .ifPresent(findParticipants::setLeagueWinRecord);

            Optional.ofNullable(participants.getLeagueDrawRecord())
                    .ifPresent(findParticipants::setLeagueDrawRecord);

            Optional.ofNullable(participants.getLeagueLoseRecord())
                    .ifPresent(findParticipants::setLeagueLoseRecord);

            Optional.ofNullable(participants.getHonorScore())
                    .ifPresent(findParticipants::setHonorScore);

            Optional.ofNullable(participants.getAgeType())
                    .ifPresent(findParticipants::setAgeType);

            Optional.ofNullable(participants.getLocationType())
                    .ifPresent(findParticipants::setLocationType);

            Optional.ofNullable(participants.getManagerName())
                    .ifPresent(findParticipants::setManagerName);

            Optional.ofNullable(participants.getSubManagerName())
                    .ifPresent(findParticipants::setSubManagerName);

            Optional.ofNullable(participants.getUniformType())
                    .ifPresent(findParticipants::setUniformType);

        /*Optional.ofNullable(participants.getMostGoals())
                .ifPresent(findParticipants::setMostGoals);

        Optional.ofNullable(participants.getMostAssist())
                .ifPresent(findParticipants::setMostAssist);

        Optional.ofNullable(participants.getMostMom())
                .ifPresent(findParticipants::setMostMom);*/
            participantsRepository.save(findParticipants);
        } catch (BusinessLogicException e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(e.getExceptions());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.PARTICIPANTS_NOT_PATCHED);
        }
        return participants;
    }

    public void updateForLeagueMatchEnd(
            Long homeTeamScore
            , Long awayTeamScore
            ,Long homeTeamLeagueListId
            ,Long awayTeamLeagueListId
    ) {
        try {
            if (homeTeamLeagueListId == null || awayTeamLeagueListId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Participants findHomeTeamParticipants = findVerifiedParticipants(homeTeamLeagueListId);
            Participants findAwayTeamParticipants = findVerifiedParticipants(awayTeamLeagueListId);

            //homeTeam 승리한 경우
            if(homeTeamScore>awayTeamScore){
                findHomeTeamParticipants.setHonorScore(findHomeTeamParticipants.getHonorScore()+300L);
                findHomeTeamParticipants.setLeagueMatchPoints(findHomeTeamParticipants.getLeagueMatchPoints()+3L);
                findHomeTeamParticipants.setLeagueWinRecord(findHomeTeamParticipants.getLeagueWinRecord()+1L);
                findHomeTeamParticipants.setLeagueMatchCount(findHomeTeamParticipants.getLeagueMatchCount()+1L);

                findAwayTeamParticipants.setHonorScore(findAwayTeamParticipants.getHonorScore()+10L);
                findAwayTeamParticipants.setLeagueLoseRecord(findAwayTeamParticipants.getLeagueLoseRecord()+1L);
                findAwayTeamParticipants.setLeagueMatchCount(findAwayTeamParticipants.getLeagueMatchCount()+1L);

                //homeTeam 패배한 경우
            } else if(homeTeamScore<awayTeamScore){
                findHomeTeamParticipants.setHonorScore(findHomeTeamParticipants.getHonorScore()+10L);
                findHomeTeamParticipants.setLeagueLoseRecord(findHomeTeamParticipants.getLeagueLoseRecord()+1L);
                findHomeTeamParticipants.setLeagueMatchCount(findHomeTeamParticipants.getLeagueMatchCount()+1L);

                findAwayTeamParticipants.setHonorScore(findAwayTeamParticipants.getHonorScore()+300L);
                findAwayTeamParticipants.setLeagueMatchPoints(findAwayTeamParticipants.getLeagueMatchPoints()+3L);
                findAwayTeamParticipants.setLeagueWinRecord(findAwayTeamParticipants.getLeagueWinRecord()+1L);
                findAwayTeamParticipants.setLeagueMatchCount(findAwayTeamParticipants.getLeagueMatchCount()+1L);

                //무승부인 경우
            } else {
                findHomeTeamParticipants.setHonorScore(findHomeTeamParticipants.getHonorScore()+100L);
                findHomeTeamParticipants.setLeagueMatchPoints(findHomeTeamParticipants.getLeagueMatchPoints()+1L);
                findHomeTeamParticipants.setLeagueDrawRecord(findHomeTeamParticipants.getLeagueDrawRecord()+1L);
                findHomeTeamParticipants.setLeagueMatchCount(findHomeTeamParticipants.getLeagueMatchCount()+1L);

                findAwayTeamParticipants.setHonorScore(findAwayTeamParticipants.getHonorScore()+100L);
                findAwayTeamParticipants.setLeagueMatchPoints(findAwayTeamParticipants.getLeagueMatchPoints()+1L);
                findAwayTeamParticipants.setLeagueDrawRecord(findAwayTeamParticipants.getLeagueDrawRecord()+1L);
                findAwayTeamParticipants.setLeagueMatchCount(findAwayTeamParticipants.getLeagueMatchCount()+1L);

            }
            participantsRepository.save(findHomeTeamParticipants);
            participantsRepository.save(findAwayTeamParticipants);
            log.info("LEAGUE_MATCH_END ABOUT HOME_TEAM TO PARTICIPANTS_REPOSITORY:{}", findHomeTeamParticipants);
            log.info("LEAGUE_MATCH_END ABOUT AWAY_TEAM TO PARTICIPANTS_REPOSITORY:{}", findAwayTeamParticipants);
        } catch (BusinessLogicException e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(e.getExceptions());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.PARTICIPANTS_NOT_PATCHED);
        }
    }

    public Participants findParticipants(long participantsId) {
        return findVerifiedParticipants(participantsId);
    }

    public List<Participants> findParticipantsNewest() {
        return participantsRepository.findLeagueParticipantsNewest();
    }

    public List<Participants> findParticipantsLatest() {
        return participantsRepository.findLeagueParticipantsLatest();
    }

    public List<Participants> findHonorScore() {
        return participantsRepository.findHonorScore();
    }


    public List<Participants> findAllLeaguesByLeagueId(long leagueId) {
        return participantsRepository.findAllLeaguesByLeagueId(leagueId);
    }

    public List<Participants> findAllParticipants() {
        return participantsRepository.findAll();
    }

    public void deleteParticipants(long participantsId) {
        try {
            Participants findParticipants = findVerifiedParticipants(participantsId);
            participantsRepository.delete(findParticipants);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessLogicException(Exceptions.PARTICIPANTS_NOT_DELETED);
        }
    }

    public Participants findVerifiedParticipants(long participantsId) {
        Optional<Participants> optionalLeague = participantsRepository.findById(participantsId);
        return optionalLeague.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.COMMENT_NOT_FOUND));
    }
}

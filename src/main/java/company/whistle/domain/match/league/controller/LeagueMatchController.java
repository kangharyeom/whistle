package company.whistle.domain.match.league.controller;

import company.whistle.domain.league.domain.service.LeagueService;
import company.whistle.domain.match.league.dto.*;
import company.whistle.domain.match.league.mapper.LeagueMatchMapper;
import company.whistle.domain.match.league.service.LeagueMatchService;
import company.whistle.domain.league.participants.service.ParticipantsService;
import company.whistle.domain.match.league.entity.LeagueMatch;
import company.whistle.global.response.MultiResponseDto;
import company.whistle.domain.team.domain.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/matches/league")
public class LeagueMatchController {
    private final LeagueMatchService leagueMatchService;
    private final LeagueMatchMapper leagueMatchMapper;
    private final TeamService teamService;
    private final ParticipantsService participantsService;
    private final LeagueService leagueService;

    @PostMapping
    public ResponseEntity<LeagueMatchResponseDto> postLeagueMatch(@Validated @RequestBody LeagueMatchPostDto requestBody) {

        LeagueMatch leagueMatch = leagueMatchService.createLeagueMatch(leagueMatchMapper.leagueMatchPostDtoToLeagueMatch(requestBody)
                , requestBody.getHomeTeamUserId()
                , requestBody.getAwayTeamUserId()
                , requestBody.getHomeTeamId()
                , requestBody.getAwayTeamId()
                , requestBody.getHomeTeamLeagueListId()
                , requestBody.getAwayTeamLeagueListId()
        );

        LeagueMatchResponseDto leagueMatchResponseDto = leagueMatchMapper.leagueMatchPostToLeagueMatchResponse(
                leagueMatch
                , requestBody.getHomeTeamUserId()
                , requestBody.getAwayTeamUserId()
                , requestBody.getHomeTeamId()
                , requestBody.getAwayTeamId()
                , requestBody.getHomeTeamLeagueListId()
                , requestBody.getAwayTeamLeagueListId()
        );
        log.info("LEAGUE_MATCH POST COMPLETE:{}", leagueMatchResponseDto.toString());

        return ResponseEntity.ok(leagueMatchResponseDto);
    }

    @GetMapping("/{leagueMatchId}")
    public ResponseEntity<LeagueMatchResponseDto> getLeagueMatch(@PathVariable("leagueMatchId") Long leagueMatchId) {
        LeagueMatch leagueMatch = leagueMatchService.findLeagueMatch(leagueMatchId);
        LeagueMatchResponseDto leagueMatchResponseDto = leagueMatchMapper.leagueMatchToLeagueMatchResponse(leagueMatch);

        return ResponseEntity.ok(leagueMatchResponseDto);
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<LeagueMatchResponseDto>> getLeagueMatches(@Positive @RequestParam(value = "page", defaultValue = "1") int page,
                                      @Positive @RequestParam(value = "size", defaultValue = "40") int size){

        Page<LeagueMatch> pageLeagueMatches = leagueMatchService.findLeagueMatches(page - 1, size);
        List<LeagueMatch> leagueMatches = pageLeagueMatches.getContent();
        log.info("전체 요청 :" + leagueMatches);
        return new ResponseEntity<>(
                new MultiResponseDto<>(leagueMatchMapper.leagueMatchesToLeagueMatchesResponse(leagueMatches),
                        pageLeagueMatches),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<LeagueMatchListDto> getLeagueMatchSearch(@RequestParam(value = "keyword",required = false) String keyword) {
        List<LeagueMatch> leagueMatches = leagueMatchService.findAllSearch(keyword);
        LeagueMatchListDto leagueMatchListDto = leagueMatchMapper.leagueMatchListDtoToLeagueMatchResponse(leagueMatches);

        return ResponseEntity.ok(leagueMatchListDto);
    }

    @GetMapping("/league/search/username")
    public ResponseEntity<LeagueMatchListDto> getLeagueMatchSearchByUserName(@RequestParam(value = "name",required = false) String name) {
        List<LeagueMatch> leagueMatches = leagueMatchService.findAllSearchByUserName(name);
        LeagueMatchListDto leagueMatchListDto = leagueMatchMapper.leagueMatchListDtoToLeagueMatchResponse(leagueMatches);

        return ResponseEntity.ok(leagueMatchListDto);
    }

    @GetMapping("/league/newest")
    public ResponseEntity<List<LeagueMatchResponseDto>> getLeagueMatchesNewest() {
        List<LeagueMatch> leagueMatches = leagueMatchService.findLeagueMatchesNewest();
        List<LeagueMatchResponseDto> leagueMatchResponseDto = leagueMatchMapper.leagueMatchesToLeagueMatchesResponse(leagueMatches);

        return ResponseEntity.ok(leagueMatchResponseDto);
    }

    @GetMapping("/league/latest")
    public ResponseEntity<List<LeagueMatchResponseDto>> getLeagueMatchesLatest() {
        List<LeagueMatch> leagueMatches = leagueMatchService.findLeagueMatchesLatest();
        List<LeagueMatchResponseDto> leagueMatchResponseDto = leagueMatchMapper.leagueMatchesToLeagueMatchesResponse(leagueMatches);

        return ResponseEntity.ok(leagueMatchResponseDto);
    }

    @PatchMapping("/{leagueMatchId}")
    public ResponseEntity<LeagueMatchResponseDto> patchLeagueMatch(@RequestBody LeagueMatchPatchDto requestBody,
                                       @PathVariable("leagueMatchId") Long leagueMatchId) {
        LeagueMatch leagueMatch = leagueMatchService.updateLeagueMatch(
                leagueMatchMapper.leagueMatchPatchDtoToLeagueMatch(requestBody),leagueMatchId);

        LeagueMatchResponseDto matchResponse = leagueMatchMapper.leagueMatchToLeagueMatchResponse(leagueMatch);

        return ResponseEntity.ok(matchResponse);
    }

    @PatchMapping("/end/{leagueMatchId}")
    public ResponseEntity<LeagueMatchEndResponseDto> patchLeagueMatchEnd(@RequestBody LeagueMatchEndDto requestBody
            , @PathVariable("leagueMatchId") Long leagueMatchId
    ) {
        LeagueMatch leagueMatch = leagueMatchService.updateLeagueMatchEnd(
                leagueMatchMapper.leagueMatchEndDtoToLeagueMatch(requestBody)
                ,leagueMatchId
        );

        leagueMatch.setLeagueMatchId(leagueMatchId);
        LeagueMatchEndResponseDto matchResponse = leagueMatchMapper.leagueMatchToLeagueMatchEndResponse(leagueMatch);
        long homeTeamScore = matchResponse.getHomeTeamScore();
        long awayTeamScore = matchResponse.getAwayTeamScore();
        log.info("LEAGUE_MATCH_END PATCH COMPLETE:{}", matchResponse.toString());

        leagueMatchService.updateForLeagueMatchEnd(homeTeamScore, awayTeamScore, leagueMatchId);
        teamService.updateForLeagueMatchEnd(homeTeamScore,awayTeamScore, requestBody.getHomeTeamId(),requestBody.getAwayTeamId());
        participantsService.updateForLeagueMatchEnd(homeTeamScore, awayTeamScore, requestBody.getHomeTeamLeagueListId(), requestBody.getAwayTeamLeagueListId());
        leagueService.updateForLeagueMatchEnd(requestBody.getLeagueId());
        leagueService.checkEndTheLeague(requestBody.getLeagueId());

        return ResponseEntity.ok(matchResponse);
    }

    @DeleteMapping("/{leagueMatchId}")
    public ResponseEntity<HttpStatus> deleteLeagueMatch(@PathVariable("leagueMatchId") Long leagueMatchId) {
        leagueMatchService.deleteLeagueMatch(leagueMatchId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}

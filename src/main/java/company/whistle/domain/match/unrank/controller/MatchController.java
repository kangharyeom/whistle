package company.whistle.domain.match.unrank.controller;

import company.whistle.domain.match.unrank.dto.*;
import company.whistle.domain.match.unrank.entity.Match;
import company.whistle.domain.match.unrank.mapper.MatchMapper;
import company.whistle.domain.match.unrank.service.MatchService;
import company.whistle.global.response.MultiResponseDto;
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
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchService matchService;
    private final MatchMapper matchMapper;

    @PostMapping
    public ResponseEntity<BothTeamInfoResponseDto> postMatch(@Validated @RequestBody HomeTeamPostDto requestBody) {
//    public ResponseEntity<BothTeamInfoResponseDto> postMatch(@Validated @RequestBody MatchPostDto requestBody) {

        Match match = matchService.createHomeTeamToMatch(matchMapper.homeTeamPostDtoToMatch(requestBody), requestBody.getHomeTeamUserId(),requestBody.getHomeTeamId());
        BothTeamInfoResponseDto matchResponseDto = matchMapper.matchBothTeamResponse(match);
//        BothTeamInfoResponseDto matchResponseDto = matchMapper.matchBothTeamResponse(match);
        log.info("UN_RANK MATCH POST COMPLETE: {}", matchResponseDto.toString());


        return ResponseEntity.ok(matchResponseDto);
    }

    @PostMapping("/{matchId}")
    public ResponseEntity<BothTeamInfoResponseDto> postAwayTeamForMatch(@RequestBody AwayTeamPostDto requestBody,
                                                                        @PathVariable("matchId") Long matchId) {
        requestBody.updateId(matchId);
        Match match = matchService.postAwayTeamForMatch(
                matchMapper.matchPatchDtoToBothMatch(requestBody),
                matchId,
                requestBody.getMatchApplyId(), requestBody.getAwayTeamUserId(), requestBody.getAwayTeamId());

        BothTeamInfoResponseDto matchResponse = matchMapper.matchBothTeamResponse(match);
        log.info("UN_RANK MATCH AWAY_TEAM CREATE COMPLETE: {}", matchResponse.toString());

        return ResponseEntity.ok(matchResponse);
    }

    @PostMapping("/{matchId}/end")
//    @PatchMapping("/{matchId}/end")
    public ResponseEntity<MatchEndResponseDto> matchEnd(@RequestBody MatchEndDto requestBody,
                                                          @PathVariable("matchId") Long matchId) {
        requestBody.updateId(matchId);
        Match match = matchService.matchEnd(
                matchMapper.matchEndDtoToMatch(requestBody), matchId,
                requestBody.getHomeTeamScore(), requestBody.getAwayTeamScore(),
                requestBody.getHomeTeamId(), requestBody.getAwayTeamId());

        MatchEndResponseDto matchResponse = matchMapper.MatchEndResponseDtoTeamResponse(match);
        log.info("UN_RANK MATCH_END PATCH COMPLETE: {}", matchResponse.toString());

        return ResponseEntity.ok(matchResponse);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<BothTeamInfoResponseDto> getMatch(@PathVariable("matchId") Long matchId) {
        Match match = matchService.findMatch(matchId);
        BothTeamInfoResponseDto matchResponseDto = matchMapper.matchBothTeamResponse(match);

        return ResponseEntity.ok(matchResponseDto);
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<MatchResponseDto>> getMatches(@Positive @RequestParam(value = "page", defaultValue = "1") int page,
                                     @Positive @RequestParam(value = "size", defaultValue = "40") int size){

        Page<Match> pageMatches = matchService.findMatches(page - 1, size);
        List<Match> matches = pageMatches.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(matchMapper.matchesToMatchesResponse(matches),
                        pageMatches),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<MatchListDto> getSearch(@RequestParam(value = "keyword",required = false) String keyword) {
        List<Match> matches = matchService.findAllSearch(keyword);
        MatchListDto matchListDto = matchMapper.matchListDtoToMatchResponse(matches);

        return ResponseEntity.ok(matchListDto);
    }

    @GetMapping("/search/username")
    public ResponseEntity<MatchListDto> getSearchByUserName(@RequestParam(value = "name",required = false) String name) {
        List<Match> matches = matchService.findAllSearchByUserName(name);
        MatchListDto matchListDto = matchMapper.matchListDtoToMatchResponse(matches);

        return ResponseEntity.ok(matchListDto);
    }

    @GetMapping("/newest")
    public ResponseEntity<List<MatchResponseDto>> getMatchesNewest() {
        List<Match> matches = matchService.findMatchesNewest();
        List<MatchResponseDto> matchResponseDto = matchMapper.matchesToMatchesResponse(matches);

        return ResponseEntity.ok(matchResponseDto);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<MatchResponseDto>> getMatchesLatest() {
        List<Match> matches = matchService.findMatchesLatest();
        List<MatchResponseDto> matchResponseDto = matchMapper.matchesToMatchesResponse(matches);

        return ResponseEntity.ok(matchResponseDto);
    }

    @PatchMapping("/{matchId}")
    public ResponseEntity<MatchResponseDto> patchMatch(@RequestBody MatchPatchDto requestBody,
                                       @PathVariable("matchId") Long matchId) {
        requestBody.updateId(matchId);
        Match match = matchService.updateMatch(
                matchMapper.matchPatchDtoToMatch(requestBody), matchId);

        MatchResponseDto matchResponse = matchMapper.matchToMatchResponse(match);
        log.info("UN_RANK MATCH PATCH COMPLETE: {}", matchResponse.toString());


        return ResponseEntity.ok(matchResponse);
    }



    @DeleteMapping("/{matchId}")
    public ResponseEntity<HttpStatus> deleteMatch(@PathVariable("matchId") Long matchId) {
        matchService.deleteMatch(matchId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}

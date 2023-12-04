package company.whistle.domain.apply.league.controller;

import company.whistle.domain.apply.league.dto.*;
import company.whistle.domain.apply.league.entity.LeagueApply;
import company.whistle.domain.apply.league.mapper.LeagueApplyMapper;
import company.whistle.domain.apply.league.service.LeagueApplyService;
import company.whistle.domain.board.comment.dto.CommentResponseDto;
import company.whistle.domain.board.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/applies/leagues")
public class LeagueApplyController {
    private final LeagueApplyService leagueApplyService;
    private final LeagueApplyMapper leagueApplyMapper;

    /*
     * 리그 가입 신청
     */
    @PostMapping("/{leagueId}")
    public ResponseEntity<LeagueApplyResponseDto> postLeagueApply(@Validated @RequestBody LeagueApplyPostDto requestBody) {
        LeagueApply leagueApply = leagueApplyService.createLeagueApply(leagueApplyMapper.leagueApplyPostDtoToLeagueApply(requestBody),requestBody.getUserId(), requestBody.getLeagueId(), requestBody.getTeamId());
        LeagueApplyResponseDto leagueApplyResponseDto = leagueApplyMapper.leagueApplyToLeagueApplyResponse(leagueApply);
        log.info("MATCH_APPLY POST COMPLETE: {}",leagueApplyResponseDto.toString());

        return ResponseEntity.ok(leagueApplyResponseDto);
    }

    /*
     * leagueApplyId 단위 리그 가입 신청 단건 조회
     */
    @GetMapping("/{leagueId}/{leagueApplyId}")
    public ResponseEntity<LeagueApplyResponseDto> getAppliesByLeagueApplyId(@PathVariable("leagueApplyId") Long leagueApplyId){
        LeagueApply leagueApply = leagueApplyService.findLeagueApply(leagueApplyId);
        LeagueApplyResponseDto leagueApplyResponse = leagueApplyMapper.leagueApplyToLeagueApplyResponse(leagueApply);
        log.info("TOTAL LEAGUE_APPLY INFO:" + leagueApplyResponse);
        return ResponseEntity.ok(leagueApplyResponse);
    }

    /*
     * leagueApplyId 단위 리그 가입 신청 전체 조회
     */
    @GetMapping("/{leagueId}")
    public ResponseEntity<LeagueApplyListDto> getAppliesByLeagueId(@PathVariable("leagueId") Long leagueId){

        List<LeagueApply> applies = leagueApplyService.findAllByLeagueId(leagueId);
        log.info("TOTAL LEAGUE_APPLIES INFO:" + applies);
        return new ResponseEntity<>(leagueApplyMapper.leagueApplyListDtoToLeagueApplyResponse(applies),
                HttpStatus.OK);
    }


    /*
     * leagueApply 제거
     */
    @DeleteMapping("/{leagueId}/{leagueApplyId}")
    public ResponseEntity<HttpStatus> deleteApply(@PathVariable("leagueApplyId") Long leagueApplyId) {
        leagueApplyService.deleteApply(leagueApplyId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}


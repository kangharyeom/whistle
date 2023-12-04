package company.whistle.domain.league.participants.controller;

import company.whistle.domain.league.participants.dto.ParticipantsPatchDto;
import company.whistle.domain.league.participants.dto.ParticipantsPostDto;
import company.whistle.domain.league.participants.dto.ParticipantsResponseDto;
import company.whistle.domain.league.participants.entity.Participants;
import company.whistle.domain.league.participants.mapper.ParticipantsMapper;
import company.whistle.domain.league.participants.service.ParticipantsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Validated
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/leagues/{leagueId}/participants")
public class ParticipantsController {
    private final ParticipantsService participantsService;
    private final ParticipantsMapper participantsMapper;

    @PostMapping
    public ResponseEntity<ParticipantsResponseDto> postParticipants(@RequestBody ParticipantsPostDto requestBody) {

        Participants participants = participantsService.createParticipants(participantsMapper.participantsPostDtoToParticipants(requestBody),
                requestBody.getUserId(),
                requestBody.getTeamId(),
                requestBody.getLeagueId(),
                requestBody.getLeagueApplyId()
        );

        ParticipantsResponseDto participantsResponse = participantsMapper.participantsToParticipantsResponse(participants);
        log.info("PARTICIPANTS POST COMPLETE:{}", participantsResponse);

        return ResponseEntity.ok(participantsResponse);
    }

    @GetMapping("/{participantsId}")
    public ResponseEntity<ParticipantsResponseDto> getParticipant(@PathVariable("participantsId") Long participantsId) {
        Participants participants = participantsService.findParticipants(participantsId);
        ParticipantsResponseDto participantsResponse = participantsMapper.participantsToParticipantsResponse(participants);

        return ResponseEntity.ok(participantsResponse);
    }

    @GetMapping
    public ResponseEntity<List<ParticipantsResponseDto>> getParticipants() {

        List<Participants> participants = participantsService.findAllParticipants();
        return new ResponseEntity<>(participantsMapper.participantssToParticipantsResponse(participants),
                HttpStatus.OK);
    }

    @GetMapping("/newest")
    public ResponseEntity<List<ParticipantsResponseDto>> getParticipantsNewest() {
        List<Participants> participantss = participantsService.findParticipantsNewest();
        List<ParticipantsResponseDto> participantsResponseDtos = participantsMapper.participantssToParticipantsResponse(participantss);

        return ResponseEntity.ok(participantsResponseDtos);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ParticipantsResponseDto>> getParticipantsLatest() {
        List<Participants> participantss = participantsService.findParticipantsLatest();
        List<ParticipantsResponseDto> participantsResponseDtos = participantsMapper.participantssToParticipantsResponse(participantss);

        return ResponseEntity.ok(participantsResponseDtos);
    }

    @GetMapping("/score")
    public ResponseEntity<List<ParticipantsResponseDto>> getParticipantsHonorScore() {
        List<Participants> participantss = participantsService.findHonorScore();
        List<ParticipantsResponseDto> participantsResponseDtos = participantsMapper.participantssToParticipantsResponse(participantss);

        return ResponseEntity.ok(participantsResponseDtos);
    }

    @PatchMapping("/{participantsId}")
    public ResponseEntity<ParticipantsResponseDto> patchParticipants(@RequestBody ParticipantsPatchDto requestBody,
                                          @PathVariable("participantsId") Long participantsId) {
        Participants participants = participantsService.updateParticipants(
                participantsMapper.participantsPatchDtoToParticipants(requestBody), participantsId);

        ParticipantsResponseDto participantsResponse = participantsMapper.participantsToParticipantsResponse(participants);
        log.info("PARTICIPANTS PATCH COMPLETE:{}", participantsResponse);

        return ResponseEntity.ok(participantsResponse);
    }

    @DeleteMapping("/{participantsId}")
    public ResponseEntity<HttpStatus> deleteParticipants(@PathVariable("participantsId") Long participantsId) {
        participantsService.deleteParticipants(participantsId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
